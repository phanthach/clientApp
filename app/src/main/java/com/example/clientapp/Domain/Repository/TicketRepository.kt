package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse

interface TicketRepository {
    suspend fun getListTicket(token:String,paymentId: Int): List<TicketDetailResponse>
    suspend fun getTicketDetail(token:String,ticketId: Int): TicketResponse
}