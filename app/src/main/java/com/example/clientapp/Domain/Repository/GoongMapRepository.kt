package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.Model.Response.PlaceAutoCompleteResponse

interface GoongMapRepository {
    suspend fun getPlaceAutoComplete(input: String?): PlaceAutoCompleteResponse
    suspend fun getPlaceDetail(place_id: String?): LocationResponse
    suspend fun getDistanceMatrix(origins: String?, destinations: String?): DistanceMatrixResponse
    suspend fun getDirection(origin: String?, destination: String?): RouteLine
}