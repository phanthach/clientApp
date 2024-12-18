package com.example.clientapp.Domain.Model.Response

import com.example.clientapp.Domain.Model.Model.TripVehicle

data class TripResponse (
    val content: List<TripVehicle>,
    val totalPages: Int
)