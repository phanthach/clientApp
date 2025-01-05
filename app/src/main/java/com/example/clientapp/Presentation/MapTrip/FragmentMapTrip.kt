package com.example.clientapp.Presentation.MapTrip

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Model.Model.ListLocation
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentMapTripBinding
import com.google.android.gms.location.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMapTrip : Fragment(), ItemListener {
    private var _binding: FragmentMapTripBinding? = null
    private val binding get() = _binding!!
    private var mapView: MapView? = null
    private lateinit var database: DatabaseReference
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var locationRef: DatabaseReference? = null
    private var isLocationUpdating = false
    private val maptripViewModel: MapTripViewModel by viewModels()
    private val listLocation: MutableList<com.example.clientapp.Domain.Model.Model.Location> = mutableListOf()
    private lateinit var adapter: SelectLocationAdapter
    private var isDirectionRequested = false  // Thêm flag kiểm tra trạng thái
    private var isTracking = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        adapter = SelectLocationAdapter(mutableListOf(), this)
        binding.rvPick.adapter = adapter
        binding.rvPick.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        // Cấu hình Mapbox
        mapView?.getMapboxMap()?.loadStyleUri(
            "https://tiles.goong.io/assets/goong_map_web.json?api_key=eFpkDf32mSR7Ik1kK7tZt3TpQMY5IepOaAETGuT1"
        )
        // Cấu hình LocationRequest và LocationCallback
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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
                    val userId = activity?.intent?.getIntExtra("userId", -1) ?: -1
                    if (userId != -1) {
                        maptripViewModel.saveLocationToFirebase(location, userId)
                        maptripViewModel.setLocation(location)
                        setRoute()
                    }
                }
            }
        }
        // Bắt đầu cập nhật vị trí
        startLocationUpdates()
        getListLocation()
        getPickLocation()
        trackingMap()
        pushNotification()
    }

    private fun pushNotification() {
        val trip = activity?.intent?.getSerializableExtra("trip") as? Trip
        if (trip != null) {
            val tripId = trip.tripId
            maptripViewModel.locationSend.observe(viewLifecycleOwner) { location ->
                location?.let {
                    binding.btnContinue.setOnClickListener {
                        maptripViewModel.sendNotification(tripId, location.locationId!!, location.locationType!!)
                    }
                }
            }
        } else {
            Log.e("PushNotification", "Trip data is null")
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

    private fun setRoute(){
        val userId = activity?.intent?.getIntExtra("userId", -1) ?: -1
        var origin = ""
        var destination = ""

        // Lắng nghe sự thay đổi của location
        maptripViewModel.location.observe(viewLifecycleOwner, { location ->
            location?.let {
                origin = "${location.latitude},${location.longitude}"
            }
            // Kiểm tra nếu cả origin và destination đã sẵn sàng
            if (origin.isNotEmpty() && destination.isNotEmpty() && !isDirectionRequested) {
                isDirectionRequested = true
                maptripViewModel.getDirection(origin, destination)
            }
        })

        // Lắng nghe sự thay đổi của destination
        maptripViewModel.destination.observe(viewLifecycleOwner, { destinationLocation ->
            destination = destinationLocation
            // Kiểm tra nếu cả origin và destination đã sẵn sàng
            if (origin.isNotEmpty() && destination.isNotEmpty() && !isDirectionRequested) {
                isDirectionRequested = true
                maptripViewModel.getDirection(origin, destination)
            }
        })

        // Lắng nghe kết quả từ API chỉ đường
        maptripViewModel.routeLine.observe(viewLifecycleOwner, {
            if (it != null) {
                mapView?.mapboxMap?.getStyle { style ->
                    style.removeStyleLayer("route-layer")
                    style.removeStyleSource("route-source")
                }

                val poly = it.routes!![0].overview_polyline.points
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
                        maptripViewModel.updateGeometryInFirebase(userId, poly.toString())
                    }
                }
                isDirectionRequested = false  // Reset flag sau khi vẽ xong
            }
        })
    }
    private fun getPickLocation() {
        binding.pickUp.setOnClickListener{
            binding.pick.visibility = View.VISIBLE
        }
    }

    private fun getListLocation() {
        val trip = activity?.intent?.getSerializableExtra("trip")
        if(trip!=null){
            val routeId = (trip as Trip).routeId
            if (routeId != -1) {
                maptripViewModel.getLocations(routeId)
            }
        }
        maptripViewModel.listLocation.observe(viewLifecycleOwner, { locationListResponse ->

            listLocation.clear()
            listLocation.addAll(locationListResponse.locations!!)
            adapter.updateData(listLocation)


            locationListResponse?.let {
                if(locationListResponse.status==200){
                    Log.e("FragmentSelectLocation", "setUpLocation: ${locationListResponse.message}")
                    locationListResponse.locations!!.forEach {location ->
                        if(location.locationType==1){
                            val annotationApi = mapView?.annotations
                            val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
                            val pointAnnotationOptions = PointAnnotationOptions()
                                .withPoint(Point.fromLngLat(location.longitude!!, location.latitude!!))
                                .withIconImage(ContextCompat.getDrawable(requireContext(), R.drawable.lettera)!!.toBitmap().let { Bitmap.createScaledBitmap(it, 80, 80, false) })// Replace with your marker icon ID
                            pointAnnotationManager?.create(pointAnnotationOptions)
                        }else{
                            val annotationApi = mapView?.annotations
                            val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
                            val pointAnnotationOptions = PointAnnotationOptions()
                                .withPoint(Point.fromLngLat(location.longitude!!, location.latitude!!))
                                .withIconImage(ContextCompat.getDrawable(requireContext(), R.drawable.letterb)!!.toBitmap().let { Bitmap.createScaledBitmap(it, 80, 80, false) })// Replace with your marker icon ID
                            pointAnnotationManager?.create(pointAnnotationOptions)
                        }
                    }
                }
                else{
                    AlertDialog.Builder(requireContext())
                        .setTitle("Lỗi")
                        .setMessage("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss() // Đóng dialog
                            val inten = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(inten)
                        }.show()
                }
            }
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



    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onItemClick(position: Int) {
        binding.pick.visibility = View.GONE
        binding.pickUpLocation.text = listLocation[position].nameLocation
        maptripViewModel.setLocationSend(listLocation[position])
        if(listLocation[position].locationType==1){
            binding.letter.setImageResource(R.drawable.lettera)
        }
        else{
            binding.letter.setImageResource(R.drawable.letterb)
        }
        var destination = listLocation[position].latitude.toString() + "," + listLocation[position].longitude.toString()
        maptripViewModel.setDestination(destination)
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
        if(isSelected){
            var origin = ""
            maptripViewModel.location.observe(viewLifecycleOwner, { location ->
                location?.let {
                    origin = "${location.latitude},${location.longitude}"
                }
            })
            var destination = listLocation[position].latitude.toString() + "," + listLocation[position].longitude.toString()
            moveToLocation(listLocation[position].latitude!!, listLocation[position].longitude!!)
            maptripViewModel.getDirection(origin, destination)
            maptripViewModel.routeLine.observe(viewLifecycleOwner,{
                mapView?.mapboxMap?.getStyle { style ->
                    style.removeStyleLayer("route-layer")
                    style.removeStyleSource("route-source")
                }
                val poly = it.routes!![0].overview_polyline.points
                Log.e("FragmentSelectLocation", "onItemClickSelected: $poly")
                if(poly!=null){
                    val  directionsRouteFeature: Feature = Feature.fromGeometry(LineString.fromPolyline(poly.toString(), 5))
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
            })
        }
        else{
            mapView?.mapboxMap?.getStyle { style ->
                style.removeStyleLayer("route-layer")
                style.removeStyleSource("route-source")
            }
        }
    }
    private fun moveToLocation(lat: Double, lng: Double) {
        val mapboxMap = mapView?.getMapboxMap()
        mapboxMap?.setCamera(
            com.mapbox.maps.CameraOptions.Builder()
                .center(Point.fromLngLat(lng, lat))
                .zoom(14.0)
                .build()
        )

        val annotationApi = mapView?.annotations
        val pointAnnotationManager = annotationApi?.createPointAnnotationManager()
        val pointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(lng, lat))
            .withIconImage(ContextCompat.getDrawable(requireContext(), R.drawable.placeholder)!!.toBitmap().let { Bitmap.createScaledBitmap(it, 80, 80, false) })// Replace with your marker icon ID
        pointAnnotationManager?.create(pointAnnotationOptions)
    }
}
