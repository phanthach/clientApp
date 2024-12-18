package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Request.OrderRequest
import com.example.clientapp.Domain.Model.Response.TripResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiPaymentService {
    @POST("/submitOrder")
    suspend fun submitOrder(@Header("Authorization") token: String,
                           @Query("amount") amount: Int,
                           @Query("orderInfo") orderInfo: String,
                            @Body oderRequest: OrderRequest,
                            @Query("modId") modId: Int,
                            @Query("modIdReturn") modIdReturn: Int,
                            @Query("roundTrip") roundTrip:Int,
                            @Query("ticketPrice") ticketPrice:Int,
                            @Query("ticketPriceReturn") ticketPriceReturn:Int):Int
    @GET("api/getPayment")
    suspend fun getPayment(@Header("Authorization") token: String,
                           @Query("paymentId") paymentId: Int): Response<Payment>

}