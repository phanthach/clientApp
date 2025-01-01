package com.example.clientapp.Presentation.DetailTicket

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Model.TicketDetailResponse

class DetailTicketActivityViewModel:ViewModel() {
    private var _payment = MutableLiveData<Payment>()
    val payment: LiveData<Payment> get() = _payment

    private var _ticket = MutableLiveData<Ticket>()
    val ticket: LiveData<Ticket> get() = _ticket

    fun setPayment(payment: Payment){
        _payment.postValue(payment)
    }
    fun setTicket(ticket: Ticket){
        _ticket.postValue(ticket)
    }
}