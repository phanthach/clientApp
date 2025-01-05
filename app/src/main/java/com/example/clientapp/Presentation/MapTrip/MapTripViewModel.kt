package com.example.clientapp.Presentation.MapTrip

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.UseCase.GoongMapUseCase.GoongMapUseCase
import com.example.clientapp.Domain.UseCase.TicketUseCase.TicketUseCase
import com.example.clientapp.Domain.UseCase.TripUseCase.GetLocationUseCase
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapTripViewModel @Inject constructor(
    private val goongMapUseCase: GoongMapUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val ticketUseCase: TicketUseCase
    ): ViewModel() {
    private var _listLocation = MutableLiveData<LocationListResponse>()
    val listLocation: LiveData<LocationListResponse> = _listLocation

    private var _placeDetailPickUpResponse = MutableLiveData<LocationResponse>()
    val placeDetailPickUpResponse: LiveData<LocationResponse> = _placeDetailPickUpResponse

    private var _locationDistanceMatrixResponse = MutableLiveData<DistanceMatrixResponse>()
    val locationDistanceMatrixResponse: LiveData<DistanceMatrixResponse> = _locationDistanceMatrixResponse

    private var _routeLine = MutableLiveData<RouteLine>()
    val routeLine: LiveData<RouteLine> = _routeLine

    private var _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private var _destination = MutableLiveData<String>()
    val destination: LiveData<String> get() = _destination

    private var _locationSend = MutableLiveData<com.example.clientapp.Domain.Model.Model.Location>()
    val locationSend: LiveData<com.example.clientapp.Domain.Model.Model.Location> = _locationSend

    private var _locationReceive = MutableLiveData<String>()
    val locationReceive: LiveData<String> get() = _locationReceive

    fun setLocationSend(location: com.example.clientapp.Domain.Model.Model.Location) {
        _locationSend.value = location
    }
    fun setLocation(location: Location) {
        _location.value = location
    }
    fun setDestination(destination: String) {
        _destination.value = destination
    }
    fun saveLocationToFirebase(location: Location, userId: Int) {

        // Tạo dữ liệu lưu vào Firebase
        val locationData = mapOf(
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "timestamp" to System.currentTimeMillis()
        )
        viewModelScope.launch(Dispatchers.IO) {
            val database = FirebaseDatabase.getInstance("https://csdl-qlvx-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Locations")
            val locationRef = database.child(userId.toString())
            locationRef.setValue(locationData).addOnSuccessListener {
                println("Location saved successfully!")
            }.addOnFailureListener { e ->
                println("Error saving location: ${e.message}")
            }
        }
    }
    fun updateGeometryInFirebase(userId: Int, geometry: String) {
        // Khởi tạo ViewModelScope để thực hiện các thao tác bất đồng bộ
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Kết nối đến Firebase và chỉ cập nhật trường 'geometry'
                val database = FirebaseDatabase.getInstance("https://csdl-qlvx-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Locations")
                val locationRef = database.child(userId.toString())

                // Chỉ cập nhật trường 'geometry' mà không làm thay đổi các trường khác
                locationRef.child("geometry").setValue(geometry).addOnSuccessListener {
                    println("Geometry updated successfully!")
                }.addOnFailureListener { e ->
                    println("Error updating geometry: ${e.message}")
                }
            } catch (e: Exception) {
                // Xử lý lỗi nếu có
                println("Error: ${e.message}")
            }
        }
    }
    fun sendNotification(tripId: Int, locationId: Int, locationType: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = ticketUseCase.sendNotification(tripId, locationId, locationType)
                withContext(Dispatchers.Main){
                    _locationReceive.value = response
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun getLocations(routeId: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = getLocationUseCase.location(routeId)
                withContext(Dispatchers.Main){
                    _listLocation.value = response
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun getPlaceDetailPickUp(place_id: String?) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = goongMapUseCase.getPlaceDetail(place_id)
                withContext(Dispatchers.Main){
                    _placeDetailPickUpResponse.value = response
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun getDirection(origin: String?, destination: String?) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = goongMapUseCase.getDirection(origin, destination)
                withContext(Dispatchers.Main){
                    _routeLine.value = response
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}