package com.example.clientapp.Presentation.DetailTicket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Response.TicketResponse
import com.example.clientapp.Domain.UseCase.TicketUseCase.TicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentDetailTicketViewModel @Inject constructor(private val ticketUseCase: TicketUseCase): ViewModel() {
    private val _ticketResponse = MutableLiveData<TicketResponse>()
    val ticketResponse:LiveData<TicketResponse> get() = _ticketResponse

    fun getTicketDetail(ticketId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val ticket = ticketUseCase.getTicketDetail(ticketId)
            _ticketResponse.postValue(ticket)
        }
    }
}