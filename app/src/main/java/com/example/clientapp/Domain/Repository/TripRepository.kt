package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.Model.Response.TripResponse

interface TripRepository {
    suspend fun getTrips(token: String, page: Int, startLocation: String, endLocation: String, departureDate: String): TripResponse
    suspend fun getTripsDriver(token: String, page: Int, departureDate: String): TripResponse
    suspend fun getLocations(token: String, routeId: Int): LocationListResponse
    suspend fun updateTrip(token: String, tripId: Int, status: Int): String
    suspend fun getTrip(token: String): List<Trip>
}