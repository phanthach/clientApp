package com.example.clientapp.Presentation.CustomerTrip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Response.TicketAllResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import com.example.clientapp.Domain.UseCase.TicketUseCase.TicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerTripViewModel @Inject constructor(private val ticketUseCase: TicketUseCase):ViewModel() {
    private var _listTicketResponse = MutableLiveData<List<TicketAllResponse>>()
    val listTicketResponse: LiveData<List<TicketAllResponse>> get() = _listTicketResponse

    fun getAllTicketByTripId(tripId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val response = ticketUseCase.getAllTicketByTripId(tripId)
            _listTicketResponse.postValue(response)
        }
    }
    fun updateTicket(ticketCode: String){
        viewModelScope.launch(Dispatchers.IO) {
            ticketUseCase.updateTicket(ticketCode)
        }
    }
}