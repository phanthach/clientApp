package com.example.clientapp.Domain.UseCase.TripUseCase

import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Response.TripResponse
import com.example.clientapp.Domain.Repository.TokenRepository
import com.example.clientapp.Domain.Repository.TripRepository
import jakarta.inject.Inject

class GetTripUseCase @Inject constructor(
    private val tripRepository: TripRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun getTrips(page: Int, startLocation: String, endLocation: String, departureDate: String): TripResponse {

        val token ="Bearer " + tokenRepository.getToken()
        return tripRepository.getTrips(token!!, page, startLocation, endLocation, departureDate)
    }
    suspend fun getTripsDriver(page: Int, departureDate: String): TripResponse {
        val token ="Bearer " + tokenRepository.getToken()
        return tripRepository.getTripsDriver(token!!, page, departureDate)
    }
    suspend fun updateTrip(tripId: Int, status: Int): String {
        val token ="Bearer " + tokenRepository.getToken()
        return tripRepository.updateTrip(token!!, tripId, status)
    }
    suspend fun getTrip(): List<Trip> {
        val token ="Bearer " + tokenRepository.getToken()
        return tripRepository.getTrip(token!!)
    }
}