package com.example.clientapp.Domain.Model.Response

import com.example.clientapp.Domain.Model.Model.Location

data class LocationListResponse (
    val status: Int,
    val message: String,
    val locations: List<Location>?
)