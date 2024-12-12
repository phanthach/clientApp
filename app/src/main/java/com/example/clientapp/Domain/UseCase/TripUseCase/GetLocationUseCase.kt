package com.example.clientapp.Domain.UseCase.TripUseCase

import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Repository.TokenRepository
import com.example.clientapp.Domain.Repository.TripRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val tripRepository: TripRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun location(routeId: Int): LocationListResponse{
        val token = "Bearer ${tokenRepository.getToken()}"
        return tripRepository.getLocations(token, routeId)
    }
}