package com.example.clientapp.Presentation.VehicleDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Request.OrderRequest
import com.example.clientapp.Domain.UseCase.PaymentUseCase.PaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentPayViewModel @Inject constructor(private val paymentUseCase: PaymentUseCase): ViewModel() {
    private var _paymentResult= MutableLiveData<Int>()
    val paymentResult: LiveData<Int> = _paymentResult

    fun submitOrder(amount: Int, orderInfo: String, tickets: List<Ticket>,ticketsReturn: List<Ticket>?, modId: Int, modIdReturn: Int, roundTrip:Int, ticketPrice:Int,ticketPriceReturn:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val oderRequest = OrderRequest(tickets, ticketsReturn)
            var result = paymentUseCase.submitOrder(amount, orderInfo,oderRequest, modId,modIdReturn, roundTrip, ticketPrice,ticketPriceReturn)
            _paymentResult.postValue(result)
        }
    }
}