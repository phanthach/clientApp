package com.example.clientapp.Domain.Model.Request

data class TripRequest(
//    token: String, page: Int, size: Int, startLocation: String, endLocation: String, departureDate: String
    val token: String,
    val page: Int,
    val size: Int,
    val startLocation: String,
    val endLocation: String,
    val departureDate: String
)
