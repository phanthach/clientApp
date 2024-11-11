package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Response.TripResponse

interface TripRepository {
    suspend fun getTrips(token: String, page: Int, startLocation: String, endLocation: String, departureDate: String): TripResponse
}