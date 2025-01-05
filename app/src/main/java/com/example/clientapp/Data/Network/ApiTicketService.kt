package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketAllResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiTicketService {
    @GET("api/getListTicket")
    suspend fun getListTicket(@Header("Authorization") token: String,
                              @Query("paymentId") paymentId: Int): Response<List<TicketDetailResponse>>

    @GET("api/getDetailTicket")
    suspend fun getTicketDetail(@Header("Authorization") token: String,
                                @Query("ticketId") ticketId: Int): Response<TicketResponse>

    @POST("api/send")
    suspend fun sendNotification(@Header("Authorization") token: String,
                                 @Query("tripId") tripId: Int,
                                 @Query("locationId") locationId: Int,
                                 @Query("locationType") locationType: Int, ): Response<String>
    @GET("api/getAllTicketByTripId")
    suspend fun getAllTicketByTripId(@Header("Authorization") token: String,
                                @Query("tripId") tripId: Int): Response<List<TicketAllResponse>>

    @POST("api/updateTicketStatus")
    suspend fun updateTicket(@Header("Authorization") token: String,
                             @Query("ticketCode") ticketCode: String): Response<String>
}