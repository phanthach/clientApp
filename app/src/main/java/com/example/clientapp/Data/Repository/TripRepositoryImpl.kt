package com.example.clientapp.Data.Repository

import android.util.Log
import com.example.clientapp.Data.Network.ApiTripService
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Response.LocationListResponse
import com.example.clientapp.Domain.Model.Response.TripResponse
import com.example.clientapp.Domain.Repository.TripRepository
import jakarta.inject.Inject

class TripRepositoryImpl @Inject constructor(private val apiService: ApiTripService): TripRepository {
    override suspend fun getTrips(token: String, page: Int, startLocation: String, endLocation: String, departureDate: String): TripResponse {
        return try {
            val response = apiService.searchTrip(token,page, 10, startLocation, endLocation,departureDate)
            Log.e("TripRepositoryImpl", "getTrips: ${response.code()}")
            when(response.code()){
                200 -> response.body()!!
                401 -> throw Exception("Người dùng không hợp lệ")
                403 -> throw Exception("Phiên đăng nhập hết hạn")
                else -> throw Exception("Lỗi không xác định")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            TripResponse(emptyList(), 0)
        }
    }

    override suspend fun getTripsDriver(
        token: String,
        page: Int,
        departureDate: String
    ): TripResponse {
        return try {
            val response = apiService.searchTripDriver(token,page, 10,departureDate)
            Log.e("TripRepositoryImpl", "getTrips: ${response.code()}")
            when(response.code()){
                200 -> response.body()!!
                401 -> throw Exception("Người dùng không hợp lệ")
                403 -> throw Exception("Phiên đăng nhập hết hạn")
                else -> throw Exception("Lỗi không xác định")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            TripResponse(emptyList(), 0)
        }
    }

    override suspend fun getLocations(token: String, routeId: Int): LocationListResponse {
        return try {
            apiService.listLocations(token, routeId).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            LocationListResponse(403, "error", emptyList())
        }
    }

    override suspend fun updateTrip(token: String, tripId: Int, status: Int): String {
        return try {
            apiService.updateTrip(token, tripId, status)
        } catch (e: Exception) {
            e.printStackTrace()
            "1"
        }
    }

    override suspend fun getTrip(token: String): List<Trip> {
        return try {
            val response = apiService.getTrip(token)
            when(response.code()){
                200 -> response.body()!!
                401 -> throw Exception("Người dùng không hợp lệ")
                else -> throw Exception("Lỗi không xác định")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}