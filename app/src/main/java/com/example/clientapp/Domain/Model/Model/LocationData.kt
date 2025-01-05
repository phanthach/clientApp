package com.example.clientapp.Domain.Model.Model

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val geometry: String = "",
    val timestamp: Long = 0
)