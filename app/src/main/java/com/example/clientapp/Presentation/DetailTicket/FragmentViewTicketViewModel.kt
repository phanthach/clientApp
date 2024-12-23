package com.example.clientapp.Presentation.DetailTicket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.UseCase.PaymentUseCase.PaymentUseCase
import com.example.clientapp.Domain.UseCase.TicketUseCase.TicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentViewTicketViewModel @Inject constructor(private val ticketUseCase: TicketUseCase, private val paymentUseCase: PaymentUseCase):ViewModel() {
    private var _listTicket = MutableLiveData<List<TicketDetailResponse>>()
    val listTicket: LiveData<List<TicketDetailResponse>> get() = _listTicket

    private var _payment = MutableLiveData<Payment>()
    val payment: LiveData<Payment> get() = _payment

    private var _paymentCheck = MutableLiveData<Payment>()
    val paymentCheck: LiveData<Payment> get() = _paymentCheck

    fun setPayment(payment: Payment){
        Log.d("FragmentViewTicketViewModel", "setPayment: $payment")
        _payment.postValue(payment)
    }

    fun getListTicket(paymentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val list = ticketUseCase.getListTicket(paymentId)
            _listTicket.postValue(list)
        }
    }
    fun updatePayment(paymentId: Int, paymentStatus: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val payment = paymentUseCase.updatePaymentStatus(paymentId, paymentStatus)
            Log.e("FragmentViewTicketViewModel", "updatePayment: $payment")
            _paymentCheck.postValue(payment)
        }
    }
}