package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.LayoutSeat

interface LayoutRepository {
    suspend fun getLayout(token: String, layoutId: Int): LayoutSeat
}