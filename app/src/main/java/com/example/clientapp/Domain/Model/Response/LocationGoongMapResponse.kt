package com.example.clientapp.Domain.Model.Response

data class LocationResponse(
    val result: Resultt?,
    val status: String
)

data class Resultt(
    val place_id: String,
    val formatted_address: String,
    val geometry: Geometry
)

data class Geometry(
    val location: Locations
)

data class Locations(
    val lat: Double,
    val lng: Double
)
