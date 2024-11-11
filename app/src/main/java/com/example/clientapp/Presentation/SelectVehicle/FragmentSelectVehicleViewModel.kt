package com.example.clientapp.Presentation.SelectVehicle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Response.TripResponse
import com.example.clientapp.Domain.UseCase.TripUseCase.GetTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FragmentSelectVehicleViewModel @Inject constructor(private val getTripUseCase: GetTripUseCase): ViewModel() {
    private var _trips = MutableLiveData<TripResponse>()
    val trips: LiveData<TripResponse> get() = _trips

    fun getTrips(page: Int, startLocation: String, endLocation: String, departureDate: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = getTripUseCase.getTrips(page, startLocation, endLocation, departureDate)
                withContext(Dispatchers.Main){
                    _trips.postValue(response)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    e.printStackTrace()
                    _trips.postValue(TripResponse(emptyList(), 0))
                }
            }
        }
    }
}