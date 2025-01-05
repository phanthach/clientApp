package com.example.clientapp.Presentation.MapTripClient

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.LocationData
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.UseCase.GoongMapUseCase.GoongMapUseCase
import com.example.clientapp.Domain.UseCase.TripUseCase.GetLocationUseCase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val goongMapUseCase: GoongMapUseCase,
    private val getLocationUseCase: GetLocationUseCase
    ): ViewModel() {
    private var _listLocation = MutableLiveData<LocationListResponse>()
    val listLocation: LiveData<LocationListResponse> = _listLocation

    private var _placeDetailPickUpResponse = MutableLiveData<LocationResponse>()
    val placeDetailPickUpResponse: LiveData<LocationResponse> = _placeDetailPickUpResponse

    private var _locationDistanceMatrixResponse = MutableLiveData<DistanceMatrixResponse>()
    val locationDistanceMatrixResponse: LiveData<DistanceMatrixResponse> = _locationDistanceMatrixResponse

    private var _routeLine = MutableLiveData<String>()
    val routeLine: LiveData<String> = _routeLine

    private var _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private var _destination = MutableLiveData<String>()
    val destination: LiveData<String> get() = _destination

    private var _locationData = MutableLiveData<LocationData>()
    val locationData: LiveData<LocationData> = _locationData

    fun setLocation(location: Location) {
        _location.value = location
    }
    fun setDestination(destination: String) {
        _destination.value = destination
    }
    fun getLocationFromFirebase(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val database = FirebaseDatabase.getInstance("https://csdl-qlvx-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Locations")
            val locationRef = database.child(userId.toString())
            locationRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val locationData = dataSnapshot.getValue(LocationData::class.java)
                    locationData?.let {
                        viewModelScope.launch(Dispatchers.Main) {
                            _locationData.postValue(it)
                            _routeLine.postValue(it.geometry)
                        }
                    } ?: run {
                        println("No location data found for userId: $userId")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Error retrieving location: ${databaseError.message}")
                }
            })
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
}