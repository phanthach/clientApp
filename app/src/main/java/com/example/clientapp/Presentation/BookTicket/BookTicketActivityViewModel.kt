package com.example.clientapp.Presentation.BookTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientapp.Domain.Model.Model.TripVehicle

class BookTicketActivityViewModel:ViewModel() {
//    Quản lý thông tin vé
    private var _trips = MutableLiveData<TripVehicle>()
    val trips: LiveData<TripVehicle> get() = _trips

    private var _tripsReturn = MutableLiveData<TripVehicle>()
    val tripsReturn: LiveData<TripVehicle> get() = _tripsReturn

    private var _listNameSeat = MutableLiveData<List<String>>()
    val listNameSeat: LiveData<List<String>> get() = _listNameSeat

    private var _listNameSeatReturn = MutableLiveData<List<String>>()
    val listNameSeatReturn: LiveData<List<String>> get() = _listNameSeatReturn

    private var _listSeatID = MutableLiveData<List<Int>>()
    val listSeatID: LiveData<List<Int>> get() = _listSeatID

    private var _listSeatIDReturn = MutableLiveData<List<Int>>()
    val listSeatIDReturn: LiveData<List<Int>> get() = _listSeatIDReturn

    private var _priceSeat = MutableLiveData<Int>()
    val priceSeat: LiveData<Int> get() = _priceSeat

    private var _priceSeatReturn = MutableLiveData<Int>()
    val priceSeatReturn: LiveData<Int> get() = _priceSeatReturn

    private var _locationPickUpTrip = MutableLiveData<Int>()
    val locationPickUpTrip: LiveData<Int> get() = _locationPickUpTrip

    private var _locationDropOffTrip = MutableLiveData<Int>()
    val locationDropOffTrip: LiveData<Int> get() = _locationDropOffTrip

    private var _locationPickUpTripReturn = MutableLiveData<Int>()
    val locationPickUpTripReturn: LiveData<Int> get() = _locationPickUpTripReturn

    private var _locationDropOffTripReturn = MutableLiveData<Int>()
    val locationDropOffTripReturn: LiveData<Int> get() = _locationDropOffTripReturn

    private var _locationPickUpNameTrip = MutableLiveData<String>()
    val locationPickUpNameTrip: LiveData<String> get() = _locationPickUpNameTrip

    private var _locationDropOffNameTrip = MutableLiveData<String>()
    val locationDropOffNameTrip: LiveData<String> get() = _locationDropOffNameTrip

    private var _locationPickUpNameTripReturn = MutableLiveData<String>()
    val locationPickUpNameTripReturn: LiveData<String> get() = _locationPickUpNameTripReturn

    private var _locationDropOffNameTripReturn = MutableLiveData<String>()
    val locationDropOffNameTripReturn: LiveData<String> get() = _locationDropOffNameTripReturn

//    Quản lý tìm kiếm
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

    private var listNameSeatBackUpReturn : MutableList<String> = mutableListOf()
    private var listSeatIdBackUpReturn : MutableList<Int> = mutableListOf()

    fun updateListNameSeat(seatID: Int,listNameSeat: String){
        listNameSeatBackUp.add(listNameSeat)
        listSeatIdBackUp.add(seatID)
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun updateListNameSeatReturn(seatID: Int,listNameSeat: String){
        listNameSeatBackUpReturn.add(listNameSeat)
        listSeatIdBackUpReturn.add(seatID)
        _listNameSeatReturn.postValue(listNameSeatBackUpReturn)
        _listSeatIDReturn.postValue(listSeatIdBackUpReturn)
    }
    fun removeListNameSeat(seatID: Int, nameSeat: String){
        listNameSeatBackUp.remove(nameSeat)
        listSeatIdBackUp.remove(seatID)
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun removeListNameSeatReturn(seatID: Int, nameSeat: String){
        listNameSeatBackUpReturn.remove(nameSeat)
        listSeatIdBackUpReturn.remove(seatID)
        _listNameSeatReturn.postValue(listNameSeatBackUpReturn)
        _listSeatIDReturn.postValue(listSeatIdBackUpReturn)
    }
    fun updatePriceSeat(price: Int){
        _priceSeat.postValue(price)
    }
    fun updatePriceSeatReturn(price: Int){
        _priceSeatReturn.postValue(price)
    }
    fun removeAll(){
        listNameSeatBackUp.clear()
        listSeatIdBackUp.clear()
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun removeAllReturn(){
        listNameSeatBackUpReturn.clear()
        listSeatIdBackUpReturn.clear()
        _listNameSeatReturn.postValue(listNameSeatBackUpReturn)
        _listSeatIDReturn.postValue(listSeatIdBackUpReturn)
    }
    fun getTrips(tripVehicle: TripVehicle){
        _trips.postValue(tripVehicle)
    }
    fun getTripsReturn(tripVehicle: TripVehicle){
        _tripsReturn.postValue(tripVehicle)
    }

    fun updateLocationPickUpTrip(locationID: Int, locationName: String){
        _locationPickUpTrip.postValue(locationID)
        _locationPickUpNameTrip.postValue(locationName)
    }
    fun updateLocationDropOffTrip(locationID: Int, locationName: String){
        _locationDropOffTrip.postValue(locationID)
        _locationDropOffNameTrip.postValue(locationName)
    }
    fun updateLocationPickUpTripReturn(locationID: Int, locationName: String){
        _locationPickUpTripReturn.postValue(locationID)
        _locationPickUpNameTripReturn.postValue(locationName)

    }
    fun updateLocationDropOffTripReturn(locationID: Int, locationName: String){
        _locationDropOffTripReturn.postValue(locationID)
        _locationDropOffNameTripReturn.postValue(locationName)
    }

// set thông tin tìm kiếm
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