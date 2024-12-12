package com.example.clientapp.Presentation.VehicleDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.Model.Response.PlaceAutoCompleteResponse
import com.example.clientapp.Domain.UseCase.GoongMapUseCase.GoongMapUseCase
import com.example.clientapp.Domain.UseCase.TripUseCase.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FragmentSelectLocationViewModel @Inject constructor(
    private val goongMapUseCase: GoongMapUseCase,
    private val getLocationUseCase: GetLocationUseCase
    ):ViewModel() {
    private var _placeAutoCompleteResponse = MutableLiveData<PlaceAutoCompleteResponse>()
    val placeAutoCompleteResponse: LiveData<PlaceAutoCompleteResponse> = _placeAutoCompleteResponse

    private var _placeDetailPickUpResponse = MutableLiveData<LocationResponse>()
    val placeDetailPickUpResponse: LiveData<LocationResponse> = _placeDetailPickUpResponse

    private var _placeDetailDropOffResponse = MutableLiveData<LocationResponse>()
    val placeDetailDropOffResponse: LiveData<LocationResponse> = _placeDetailDropOffResponse

    private var _listLocation = MutableLiveData<LocationListResponse>()
    val listLocation: LiveData<LocationListResponse> = _listLocation

    private var _pickUpDistanceMatrixResponse = MutableLiveData<DistanceMatrixResponse>()
    val pickUpDistanceMatrixResponse: LiveData<DistanceMatrixResponse> = _pickUpDistanceMatrixResponse

    private var _dropOffDistanceMatrixResponse = MutableLiveData<DistanceMatrixResponse>()
    val dropOffDistanceMatrixResponse: LiveData<DistanceMatrixResponse> = _dropOffDistanceMatrixResponse

    private var _routeLine = MutableLiveData<RouteLine>()
    val routeLine: LiveData<RouteLine> = _routeLine

    private var _pickupLocation = MutableLiveData<String>()
    val pickupLocation: LiveData<String> = _pickupLocation

    private var _dropoffLocation = MutableLiveData<String>()
    val dropoffLocation: LiveData<String> = _dropoffLocation

    fun getPlaceAutoComplete(input: String?) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = goongMapUseCase.getPlaceAutoComplete(input)
                withContext(Dispatchers.Main){
                    _placeAutoCompleteResponse.value = response
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

    fun getPlaceDetailDropOff(place_id: String?) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = goongMapUseCase.getPlaceDetail(place_id)
                withContext(Dispatchers.Main){
                    _placeDetailDropOffResponse.value = response
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
    fun getPickUpDistanceMatrix(origins: String?, destinations: String?) {
        Log.d("TAG", "getDropOffDistanceMatrix1: $origins $destinations")
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = goongMapUseCase.getDistanceMatrix(origins, destinations)
                Log.d("TAG", "getDropOffDistanceMatrix5: $response")
                withContext(Dispatchers.Main){
                    _pickUpDistanceMatrixResponse.value = response
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun getDropOffDistanceMatrix(origins: String?, destinations: String?) {
        try {
            viewModelScope.launch(Dispatchers.IO){
                val response = goongMapUseCase.getDistanceMatrix(origins, destinations)
                withContext(Dispatchers.Main){
                    _dropOffDistanceMatrixResponse.value = response
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
    fun setPickupLocation(location: String){
        _pickupLocation.value = location
    }
    fun setDropoffLocation(location: String){
        _dropoffLocation.value = location
    }
}