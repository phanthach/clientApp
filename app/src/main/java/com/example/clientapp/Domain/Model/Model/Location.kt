package com.example.clientapp.Domain.Model.Model

data class Location(
    val locationId: Int?,
    val routeId: Int?,
    val nameLocation: String?,
    val latitude: Double?,
    val longitude: Double?,
    val locationType: Int?,
    val arrivalTime: String?
)