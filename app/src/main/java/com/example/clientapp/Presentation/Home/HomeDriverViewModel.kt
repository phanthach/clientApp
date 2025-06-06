package com.example.clientapp.Presentation.Home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.UseCase.PaymentUseCase.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDriverViewModel @Inject constructor(private val paymentUseCase: PaymentUseCase): ViewModel() {
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

    private var _countPayment = MutableLiveData<Long>()
    val countPayment: LiveData<Long> get() = _countPayment

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
    fun countPayment(){
        viewModelScope.launch(Dispatchers.IO) {
            var count:Long = paymentUseCase.countPayment()
            _countPayment.postValue(count)
        }
    }
}