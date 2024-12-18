package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Model.LayoutSeat
import com.example.clientapp.Domain.Model.Response.TripResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiLayoutService {
    @GET("api/getLayouts")
    suspend fun getLayout(@Header("Authorization") token: String,
                           @Query("layoutId") layoutId: Int): Response<LayoutSeat>
}