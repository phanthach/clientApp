package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketAllResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse

interface TicketRepository {
    suspend fun getListTicket(token:String,paymentId: Int): List<TicketDetailResponse>
    suspend fun getTicketDetail(token:String,ticketId: Int): TicketResponse
    suspend fun sendNotification(token:String,tripId: Int,locationId: Int,locationType: Int): String
    suspend fun getAllTicketByTripId(token:String,tripId: Int): List<TicketAllResponse>
    suspend fun updateTicket(token:String,ticketCode: String): String
}