package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiTicketService {
    @GET("api/getListTicket")
    suspend fun getListTicket(@Header("Authorization") token: String,
                              @Query("paymentId") paymentId: Int): Response<List<TicketDetailResponse>>

    @GET("api/getDetailTicket")
    suspend fun getTicketDetail(@Header("Authorization") token: String,
                                @Query("ticketId") ticketId: Int): Response<TicketResponse>
}