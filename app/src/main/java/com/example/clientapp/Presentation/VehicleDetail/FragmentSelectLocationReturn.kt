package com.example.clientapp.Presentation.VehicleDetail

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Model.Model.ListLocation
import com.example.clientapp.Domain.Model.Model.Location
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.BookTicket.BookTicketActivityViewModel
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.Presentation.Pay.PayActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentSelectLocationBinding
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class FragmentSelectLocationReturn: Fragment(), ItemListener {
    private var _binding: FragmentSelectLocationBinding? = null
    private val bookTicketActivityViewModel: BookTicketActivityViewModel by activityViewModels()
    private val fragmentSelectLocationViewModel: FragmentSelectLocationViewModel by viewModels()
    private val binding get() = _binding!!
    private var mapView: MapView? = null
    private var place_id: String? = null
    private val pickUp:MutableList<Location> = mutableListOf()
    private val dropOff:MutableList<Location> = mutableListOf()
    private var checkPoint: Boolean = false
    private lateinit var adapter: SelectLocationAdapter
    private val listLocationPickUp: MutableList<ListLocation> = mutableListOf()
    private val listLocationDropOff: MutableList<ListLocation> = mutableListOf()
    private var setroute: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSelectLocationBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        adapter = SelectLocationAdapter(mutableListOf(), this)
        binding.rvPick.adapter = adapter
        binding.rvPick.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        // set styleUri to null method
        mapView?.getMapboxMap()?.loadStyleUri("https://tiles.goong.io/assets/goong_map_web.json?api_key=eFpkDf32mSR7Ik1kK7tZt3TpQMY5IepOaAETGuT1")
        bookTicketActivityViewModel.trips.observe(viewLifecycleOwner, {
            fragmentSelectLocationViewModel.getLocations(it.trip.routeId)
        })
        if(setroute==1){
            binding.pickUp.setBackgroundResource(R.drawable.boder_edittex_err)
        }
        binding.btnContinue.setOnClickListener{
            val intent = Intent(requireContext(), PayActivity::class.java)
            startActivity(intent)
        }
        setUpSearch()
        setUpLocation()
        setUpPick()
        binding.btBack.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    private fun setUpPick() {
        binding.pickUp.setOnClickListener {
            if(setroute==2 || setroute==3){
                binding.pickUp.setBackgroundResource(R.drawable.boder_edittex_err)
                binding.dropOff.setBackgroundResource(R.drawable.boder_edittex)
                setroute=1
                checkPoint=false
                binding.pick.visibility = View.VISIBLE
                setAdapterLocation()
            }
        }
        binding.dropOff.setOnClickListener {
            if(setroute==1 || setroute==3){
                binding.dropOff.setBackgroundResource(R.drawable.boder_edittex_err)
                binding.pickUp.setBackgroundResource(R.drawable.boder_edittex)
                setroute=2
                checkPoint=true
                binding.pick.visibility = View.VISIBLE
                setAdapterLocation()
            }
        }
    }
    private fun setAdapterLocation() {
        if (!checkPoint) {
            Log.e("FragmentSelectLocation", "checkPointFalse: $checkPoint")
            binding.titlePick.text = "Chọn điểm lên xe"
            // Xóa Observer của dropOffDistanceMatrixResponse nếu đã được đăng ký
            fragmentSelectLocationViewModel.dropOffDistanceMatrixResponse.removeObservers(viewLifecycleOwner)

            // Đăng ký Observer cho pickUpDistanceMatrixResponse
            fragmentSelectLocationViewModel.pickUpDistanceMatrixResponse.observe(viewLifecycleOwner) { distanceMatrixResponse ->
                distanceMatrixResponse?.let {
                    listLocationPickUp.clear()
                    Log.e("FragmentSelectLocation", "checkDropoff: ${pickUp}")
                    if (pickUp.isNotEmpty() && it.rows[0].elements.isNotEmpty()) {
                        pickUp.forEachIndexed { index, location ->
                            listLocationPickUp.add(ListLocation(location, it.rows[0].elements[index].distance.text, it.rows[0].elements[index].distance.value))
                        }
                        val sortedList = listLocationPickUp.sortedBy { it.value }
                        listLocationPickUp.clear()
                        listLocationPickUp.addAll(sortedList)
                        adapter.updateData(listLocationPickUp)
                    }
                }
            }
        } else {
            Log.e("FragmentSelectLocation", "checkPointTrue: $checkPoint")
            binding.titlePick.text = "Chọn điểm xuống xe"
            // Xóa Observer của pickUpDistanceMatrixResponse nếu đã được đăng ký
            fragmentSelectLocationViewModel.pickUpDistanceMatrixResponse.removeObservers(viewLifecycleOwner)

            // Đăng ký Observer cho dropOffDistanceMatrixResponse
            fragmentSelectLocationViewModel.dropOffDistanceMatrixResponse.observe(viewLifecycleOwner) { distanceMatrixResponse ->
                distanceMatrixResponse?.let {
                    listLocationDropOff.clear()
                    if (dropOff.isNotEmpty() && it.rows[0].elements.isNotEmpty()) {
                        dropOff.forEachIndexed { index, location ->
                            listLocationDropOff.add(ListLocation(location, it.rows[0].elements[index].distance.text, it.rows[0].elements[index].distance.value))
                        }
                        val sortedList = listLocationDropOff.sortedBy { it.value }
                        listLocationDropOff.clear()
                        listLocationDropOff.addAll(sortedList)
                        adapter.updateData(listLocationDropOff)
                    }
                }
            }
        }
    }

    private fun setUpLocation() {
        fragmentSelectLocationViewModel.placeDetailPickUpResponse.observe(
            viewLifecycleOwner,
            { locationResponse ->
                locationResponse?.let {
                    moveToLocation(
                        it.result?.geometry?.location?.lat!!,
                        it.result.geometry.location.lng!!
                    )
                    val origins =
                        "${it.result.geometry.location.lat},${it.result.geometry.location.lng}"
                    fragmentSelectLocationViewModel.listLocation.observe(viewLifecycleOwner,
                        { listLocation ->
                            if (listLocation.locations != null && listLocation.locations.isNotEmpty()) {
                                pickUp.clear()
                                pickUp.addAll(listLocation.locations.filter { it.locationType == 1 })
                                val destinationspickUp =
                                    pickUp.joinToString(separator = "|") { "${it.latitude},${it.longitude}" }
                                fragmentSelectLocationViewModel.getPickUpDistanceMatrix(
                                    origins,
                                    destinationspickUp
                                )
                            }
                        })
                }
            })
        fragmentSelectLocationViewModel.placeDetailDropOffResponse.observe(
            viewLifecycleOwner,
            { locationResponse ->
                locationResponse?.let {
                    moveToLocation(
                        it.result?.geometry?.location?.lat!!,
                        it.result.geometry.location.lng!!
                    )
                    val origins =
                        "${it.result.geometry.location.lat},${it.result.geometry.location.lng}"
                    fragmentSelectLocationViewModel.listLocation.observe(viewLifecycleOwner,
                        { listLocation ->
                            if (listLocation.locations != null && listLocation.locations.isNotEmpty()) {
                                dropOff.clear()
                                dropOff.addAll(listLocation.locations.filter { it.locationType == 2 })
                                val destinationsdropOff =
                                    dropOff.joinToString(separator = "|") { "${it.latitude},${it.longitude}" }
                                fragmentSelectLocationViewModel.getDropOffDistanceMatrix(
                                    origins,
                                    destinationsdropOff
                                )
                            }
                        })
                }
            })
        fragmentSelectLocationViewModel.listLocation.observe(viewLifecycleOwner, { locationListResponse ->
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

    private fun moveToLocation(lat: Double, lng: Double) {
        setAdapterLocation()
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

    private fun setUpSearch() {
        binding.actvSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    fragmentSelectLocationViewModel.getPlaceAutoComplete(p0.toString())
                    place_id = null
                }
            }
        })
        fragmentSelectLocationViewModel.placeAutoCompleteResponse.observe(viewLifecycleOwner, { suggestions ->
            // set adapter to actvSearch
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, suggestions.predictions.map { it.description })
            binding.actvSearch.setAdapter(adapter)
            binding.actvSearch.setOnItemClickListener { _, _, position, _ ->
                place_id = suggestions.predictions[position].place_id
                if(setroute==1){
                    fragmentSelectLocationViewModel.getPlaceDetailPickUp(place_id)
                }
                else{
                    fragmentSelectLocationViewModel.getPlaceDetailDropOff(place_id)
                }
            }
            if(place_id == null){
                binding.actvSearch.showDropDown()
            }
            else{
                binding.pick.visibility = View.VISIBLE
                binding.actvSearch.dismissDropDown()
            }
        })
    }

    override fun onItemClick(position: Int) {
        if(setroute==1){
            fragmentSelectLocationViewModel.setPickupLocation(listLocationPickUp[position].location.nameLocation!!)
            fragmentSelectLocationViewModel.pickupLocation.observe(viewLifecycleOwner, {
                binding.pickUpLocation.text = it
                binding.pickUp.setBackgroundResource(R.drawable.boder_edittex)
                binding.dropOff.setBackgroundResource(R.drawable.boder_edittex_err)
                binding.pick.visibility = View.GONE
                checkPoint=true
            })
            setroute=2
            adapter.SelectedPosition(null)
        }
        else{
            fragmentSelectLocationViewModel.setDropoffLocation(listLocationDropOff[position].location.nameLocation!!)
            fragmentSelectLocationViewModel.dropoffLocation.observe(viewLifecycleOwner, {
                binding.dropOffLocation.text = it
                binding.dropOff.setBackgroundResource(R.drawable.boder_edittex)
                binding.dropOff.setBackgroundResource(R.drawable.boder_edittex_err)
                binding.pick.visibility = View.GONE
            })
            adapter.SelectedPosition(null)
        }
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
        if(setroute==1){
            if(isSelected){
                var origin = ""
                fragmentSelectLocationViewModel.placeDetailPickUpResponse.observe(viewLifecycleOwner, { locationResponse ->
                    locationResponse?.let {
                        origin = "${it.result!!.geometry.location.lat},${it.result.geometry.location.lng}"
                    }
                })
                var destination = listLocationPickUp[position].location.latitude.toString() + "," + listLocationPickUp[position].location.longitude.toString()
                moveToLocation(listLocationPickUp[position].location.latitude!!, listLocationPickUp[position].location.longitude!!)
                fragmentSelectLocationViewModel.getDirection(origin, destination)
                fragmentSelectLocationViewModel.routeLine.observe(viewLifecycleOwner,{
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
        else{
            if(isSelected){
                var origin = ""
                fragmentSelectLocationViewModel.placeDetailDropOffResponse.observe(viewLifecycleOwner, { locationResponse ->
                    locationResponse?.let {
                        origin = "${it.result!!.geometry.location.lat},${it.result.geometry.location.lng}"
                    }
                })
                var destination = listLocationDropOff[position].location.latitude.toString() + "," + listLocationDropOff[position].location.longitude.toString()
                moveToLocation(listLocationDropOff[position].location.latitude!!, listLocationDropOff[position].location.longitude!!)
                fragmentSelectLocationViewModel.getDirection(origin, destination)
                fragmentSelectLocationViewModel.routeLine.observe(viewLifecycleOwner,{
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
    }
}