package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Model.Response.TripResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiTripService {
    @GET("api/searchtrip")
    suspend fun searchTrip(@Header("Authorization") token: String,
                           @Query("page") page: Int,
                           @Query("size") size: Int,
                           @Query("startLocation") startLocation: String,
                           @Query("endLocation") endLocation: String,
                           @Query("departureDate") departureDate: String): Response<TripResponse>
    @GET("api/listlocations")
    suspend fun listLocations(@Header("Authorization") token: String,
                                @Query("routeId") routeId: Int): Response<LocationListResponse>

    @GET("api/searchtripdriver")
    suspend fun searchTripDriver(@Header("Authorization") token: String,
                           @Query("page") page: Int,
                           @Query("size") size: Int,
                           @Query("departureDate") departureDate: String): Response<TripResponse>
    @GET("api/updateTrip")
    suspend fun updateTrip(@Header("Authorization") token: String,
                           @Query("tripId") tripId: Int,
                           @Query("status") status: Int): String

    @GET("api/getTrip")
    suspend fun getTrip(@Header("Authorization") token: String): Response<List<Trip>>
}