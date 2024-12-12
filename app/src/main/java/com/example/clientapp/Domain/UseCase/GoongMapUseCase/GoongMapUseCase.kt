package com.example.clientapp.Domain.UseCase.GoongMapUseCase

import android.util.Log
import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.Model.Response.PlaceAutoCompleteResponse
import com.example.clientapp.Domain.Repository.GoongMapRepository
import javax.inject.Inject

class GoongMapUseCase @Inject constructor(private val goongMapRepository: GoongMapRepository) {
    suspend fun getPlaceAutoComplete(input: String?): PlaceAutoCompleteResponse {
        return goongMapRepository.getPlaceAutoComplete(input)
    }
    suspend fun getPlaceDetail(place_id: String?): LocationResponse {
        return goongMapRepository.getPlaceDetail(place_id)
    }
    suspend fun getDistanceMatrix(origin: String?, destination: String?): DistanceMatrixResponse {
        Log.d("TAG", "getDropOffDistanceMatrix2: $origin $destination")
        return goongMapRepository.getDistanceMatrix(origin, destination)
    }
    suspend fun getDirection(origin: String?, destination: String?): RouteLine {
        return goongMapRepository.getDirection(origin, destination)
    }
}