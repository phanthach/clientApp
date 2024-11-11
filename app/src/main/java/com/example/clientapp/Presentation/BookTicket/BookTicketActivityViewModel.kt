package com.example.clientapp.Presentation.BookTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientapp.Domain.Model.Model.TripVehicle

class BookTicketActivityViewModel:ViewModel() {
    private var _trips = MutableLiveData<TripVehicle>()
    val trips: LiveData<TripVehicle> get() = _trips

    fun getTrips(tripVehicle: TripVehicle){
        _trips.postValue(tripVehicle)
    }
}