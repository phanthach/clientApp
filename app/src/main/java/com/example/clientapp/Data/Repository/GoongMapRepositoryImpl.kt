package com.example.clientapp.Data.Repository

import android.util.Log
import com.example.clientapp.Data.Network.ApiGoongMapService
import com.example.clientapp.Domain.Model.Model.DistanceMatrixResponse
import com.example.clientapp.Domain.Model.Model.OverviewPolyline
import com.example.clientapp.Domain.Model.Model.RouteLine
import com.example.clientapp.Domain.Model.Response.LocationResponse
import com.example.clientapp.Domain.Model.Response.PlaceAutoCompleteResponse
import com.example.clientapp.Domain.Repository.GoongMapRepository
import javax.inject.Inject

class GoongMapRepositoryImpl @Inject constructor(private val apiGoongMapService: ApiGoongMapService): GoongMapRepository {
    override suspend fun getPlaceAutoComplete(input: String?): PlaceAutoCompleteResponse {
        return try {
            apiGoongMapService.getPlaceAutoComplete(input)
        } catch (e: Exception) {
            e.printStackTrace()
            PlaceAutoCompleteResponse(emptyList(), null, "error")
        }
    }

    override suspend fun getPlaceDetail(place_id: String?): LocationResponse {
        Log.e("TAG", "getPlaceDetailDropOff2: $place_id")
        return try {
            apiGoongMapService.getPlaceDetail(place_id)
        } catch (e: Exception) {
            e.printStackTrace()
            LocationResponse(null, "error")
        }
    }

    override suspend fun getDistanceMatrix(origins: String?, destinations: String?): DistanceMatrixResponse {
        Log.d("TAG", "getDropOffDistanceMatrix3: $origins $destinations")
        return try {
            Log.d("TAG", "getDropOffDistanceMatrix4: $origins $destinations")
            apiGoongMapService.getDistanceMatrix(origins, destinations, "bike")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("GoongMapRepositoryImpl", "getDistanceMatrix: ${e.message}")
            DistanceMatrixResponse(emptyList())
        }
    }

    override suspend fun getDirection(origin: String?, destination: String?): RouteLine {
        return try {
            apiGoongMapService.getDirection(origin, destination, "bike")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("GoongMapRepositoryImpl", "getDirection: null")
            RouteLine(null)
        }
    }

}