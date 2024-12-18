package com.example.clientapp.Domain.UseCase.TripUseCase

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
}