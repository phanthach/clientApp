package com.example.clientapp.Presentation.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Response.TripResponse
import com.example.clientapp.Domain.UseCase.PaymentUseCase.PaymentUseCase
import com.example.clientapp.Domain.UseCase.TripUseCase.GetTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeDriverViewModel @Inject constructor(private val getTripUseCase: GetTripUseCase): ViewModel() {
    private var _departureDate = MutableLiveData<String>()
    val departureDate: LiveData<String> get() = _departureDate

    private var _trips = MutableLiveData<TripResponse>()
    val trips: LiveData<TripResponse> get() = _trips

    private var _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    fun setDepartureDate(departureDate: String){
        _departureDate.value = departureDate
    }

    fun getTripsDriver(page: Int, departureDate: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = getTripUseCase.getTripsDriver(page, departureDate)
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
    fun updateTrip(tripId: Int, status: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = getTripUseCase.updateTrip(tripId, status)
                withContext(Dispatchers.Main){
                    _result.postValue(response)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    e.printStackTrace()
                    _result.postValue("error")
                }
            }
        }
    }
}