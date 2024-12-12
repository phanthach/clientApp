package com.example.clientapp.Data.Repository

import android.util.Log
import com.example.clientapp.Data.Network.ApiLayoutService
import com.example.clientapp.Domain.Model.Model.LayoutSeat
import com.example.clientapp.Domain.Repository.LayoutRepository
import jakarta.inject.Inject

class LayoutRepositoryImpl @Inject constructor(private val apiLayoutService: ApiLayoutService):LayoutRepository {
    override suspend fun getLayout(token: String, layoutId: Int): LayoutSeat {
        return try {
            apiLayoutService.getLayout(token, layoutId).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            LayoutSeat("An error occurred", 401, null, null, null, null, null, null)
        }
    }

}