package com.example.clientapp.Presentation.BookTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientapp.Domain.Model.Model.TripVehicle

class BookTicketActivityViewModel:ViewModel() {
    private var _trips = MutableLiveData<TripVehicle>()
    val trips: LiveData<TripVehicle> get() = _trips

    private var _tripsReturn = MutableLiveData<TripVehicle>()
    val tripsReturn: LiveData<TripVehicle> get() = _tripsReturn

    private var _listNameSeat = MutableLiveData<List<String>>()
    val listNameSeat: LiveData<List<String>> get() = _listNameSeat

    private var _listSeatID = MutableLiveData<List<Int>>()
    val listSeatID: LiveData<List<Int>> get() = _listSeatID

    private var _priceSeat = MutableLiveData<Int>()
    val priceSeat: LiveData<Int> get() = _priceSeat



    private var _pickUpPoint = MutableLiveData<String>()
    val pickUpPoint: LiveData<String> get() = _pickUpPoint

    private var _dropOffPoint = MutableLiveData<String>()
    val dropOffPoint: LiveData<String> get() = _dropOffPoint

    private var _departureDate = MutableLiveData<String>()
    val departureDate: LiveData<String> get() = _departureDate

    private var _returnDate = MutableLiveData<String>()
    val returnDate: LiveData<String> get() = _returnDate

    private var _roundTrip = MutableLiveData<Boolean>()
    val roundTrip: LiveData<Boolean> get() = _roundTrip

    private var listNameSeatBackUp : MutableList<String> = mutableListOf()
    private var listSeatIdBackUp : MutableList<Int> = mutableListOf()

    fun updateListNameSeat(seatID: Int,listNameSeat: String){
        listNameSeatBackUp.add(listNameSeat)
        listSeatIdBackUp.add(seatID)
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun removeListNameSeat(seatID: Int, nameSeat: String){
        listNameSeatBackUp.remove(nameSeat)
        listSeatIdBackUp.remove(seatID)
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun updatePriceSeat(price: Int){
        _priceSeat.postValue(price)
    }
    fun removeAll(){
        listNameSeatBackUp.clear()
        listSeatIdBackUp.clear()
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun getTrips(tripVehicle: TripVehicle){
        _trips.postValue(tripVehicle)
    }
    fun getTripsReturn(tripVehicle: TripVehicle){
        _tripsReturn.postValue(tripVehicle)
    }




    fun setPickOffPoint(pickOffPoint: String){
        _pickUpPoint.value = pickOffPoint
    }
    fun setDropOffPoint(dropOffPoint: String){
        _dropOffPoint.value = dropOffPoint
    }
    fun setDepartureDate(departureDate: String){
        _departureDate.value = departureDate
    }
    fun setReturnDate(returnDate: String){
        _returnDate.value = returnDate
    }
    fun setRoundTrip(roundTrip: Boolean){
        _roundTrip.value = roundTrip
    }
}