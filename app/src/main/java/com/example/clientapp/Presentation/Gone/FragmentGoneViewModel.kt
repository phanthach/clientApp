package com.example.clientapp.Presentation.Gone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.UseCase.PaymentUseCase.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentGoneViewModel @Inject constructor(private val paymentUseCase: PaymentUseCase):ViewModel() {
    private var _listPayment = MutableLiveData<List<Payment>>()
    val listPayment: LiveData<List<Payment>> get() = _listPayment

    fun listPayment(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = paymentUseCase.listPaymentGone()
            _listPayment.postValue(list)
        }
    }
}