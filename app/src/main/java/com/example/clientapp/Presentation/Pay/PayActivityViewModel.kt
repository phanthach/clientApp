package com.example.clientapp.Presentation.Pay

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
class PayActivityViewModel @Inject constructor(private val paymentUseCase: PaymentUseCase): ViewModel() {
    private var _payment = MutableLiveData<Payment?>()
    val payment: LiveData<Payment?> = _payment

    fun getPayment(paymentId: Int){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = paymentUseCase.getPayment(paymentId)
                _payment.postValue(response)
            }catch (e: Exception) {
                e.printStackTrace()
                _payment.postValue(Payment(-1, -1, "", -1, "", "", -1, "",null,""))
            }
        }
    }
}