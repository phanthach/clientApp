package com.example.clientapp.Presentation.MapTripClient

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.easeTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMapBinding
    private var mapView: MapView? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var isTracking = true
    private var isTrackingVehicle = true
    private val mapViewModel: MapViewModel by viewModels()
    private var currentMarker: PointAnnotation? = null
    private lateinit var pointAnnotationManager: PointAnnotationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = binding.mapView
        mapView?.getMapboxMap()?.loadStyleUri(
            "https://tiles.goong.io/assets/goong_map_web.json?api_key=eFpkDf32mSR7Ik1kK7tZt3TpQMY5IepOaAETGuT1"
        )
        // Cấu hình LocationRequest và LocationCallback
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 10000 // 10 seconds
            fastestInterval = 5000 // 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    if (isTracking) {  // Chỉ cập nhật vị trí khi đang theo dõi
                        displayCurrentLocation(location)
                    }
                    val trip = intent.getSerializableExtra("trip") as Trip
                    val userId = trip.driverId
                    if (userId != -1) {
                        mapViewModel.getLocationFromFirebase(userId)
                        setRoute()
                    }
                }
            }
        }
        startLocationUpdates()
        trackingMap()
        binding.btBack.setOnClickListener{
            finish()
        }
    }
    private fun trackingMap() {
        binding.tracking.setOnClickListener {
            isTracking = true
        }
        // Add listener for map gestures
        mapView?.gestures?.addOnMapClickListener {
            isTracking = false
            false
        }
        mapView?.gestures?.addOnMoveListener(object : OnMoveListener {
            override fun onMoveBegin(detector: MoveGestureDetector) {
                isTracking = false
            }

            override fun onMove(detector: MoveGestureDetector): Boolean {
                return false
            }

            override fun onMoveEnd(detector: MoveGestureDetector) {}
        })
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
    private fun setRoute(){
        // Lắng nghe kết quả từ API chỉ đường
        mapViewModel.locationData.observe(this, {
            if (it != null) {
                val location = Location("")
                location.latitude = it.latitude
                location.longitude = it.longitude
                if(isTrackingVehicle){
                    isTrackingVehicle = false
                }
                displayCurrentLocationVehicle(location)
                binding.trackingVehicle.setOnClickListener {
                    moveToLocation(location.latitude, location.longitude)
                }
            }
        })
        mapViewModel.routeLine.observe(this, {
            if (it != null) {
                mapView?.mapboxMap?.getStyle { style ->
                    style.removeStyleLayer("route-layer")
                    style.removeStyleSource("route-source")
                }

                val poly = it
                if (poly != null) {
                    val directionsRouteFeature: Feature = Feature.fromGeometry(LineString.fromPolyline(poly.toString(), 5))
                    Log.e("FragmentSelectLocation", "onItemClickSelected: ${directionsRouteFeature.toJson()}")
                    mapView?.mapboxMap?.getStyle { style ->
                        val geoJsonSource = GeoJsonSource.Builder("route-source")
                            .feature(directionsRouteFeature)
                            .build()
                        style.addSource(geoJsonSource)

                        val lineLayer = LineLayer("route-layer", "route-source")
                        lineLayer.lineColor(Color.parseColor("#428ae3"))
                        lineLayer.lineWidth(5.0)
                        style.addLayer(lineLayer)
                    }
                }
            }
        })
    }
    private fun moveToLocation(lat: Double, lng: Double) {
        val point = Point.fromLngLat(lng, lat)
        val cameraOptions = CameraOptions.Builder()
            .center(point)
            .zoom(15.0)
            .build()
        mapView!!.getMapboxMap().easeTo(cameraOptions, mapAnimationOptions {
            duration(1000) // Duration of the animation in milliseconds
        })
    }
    private fun displayCurrentLocation(location: Location) {
        val point = Point.fromLngLat(location.longitude, location.latitude)
        val cameraOptions = CameraOptions.Builder()
            .center(point)
            .zoom(15.0)
            .build()
        mapView!!.getMapboxMap().easeTo(cameraOptions, mapAnimationOptions {
            duration(1000) // Duration of the animation in milliseconds
        })

        // Hiển thị biểu tượng vị trí hiện tại
        mapView!!.location.updateSettings {
            enabled = true
        }
    }
    private fun displayCurrentLocationVehicle(location: Location) {
        val point = Point.fromLngLat(location.longitude, location.latitude)

        // Initialize PointAnnotationManager if not already initialized
        if (!::pointAnnotationManager.isInitialized) {
            pointAnnotationManager = mapView!!.annotations.createPointAnnotationManager()
        }

        // If there is an old marker, delete it
        currentMarker?.let {
            pointAnnotationManager.delete(it)
        }

        // Convert 20dp to pixels
        val density = resources.displayMetrics.density
        val widthPx = (30 * density).toInt() // Width in pixels
        val heightPx = (30 * density).toInt() // Height in pixels

        // Display car icon at the current location
        mapView!!.getMapboxMap().getStyle { style ->
            // Get the car icon drawable
            val drawable = ContextCompat.getDrawable(this, R.drawable.logo)
            val bitmap = drawable?.toBitmap(widthPx, heightPx) // Resize the bitmap

            if (bitmap != null) {
                // Create and add the car icon to the map
                val pointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(point) // Location
                    .withIconImage(bitmap) // Car icon with new size
                val newMarker = pointAnnotationManager.create(pointAnnotationOptions)

                // Update the current marker
                currentMarker = newMarker
            }
        }

        // Update user location settings if needed
        mapView!!.location.updateSettings {
            enabled = true
        }
    }
}