package com.example.clientapp.Domain.Model.Model

import java.io.Serializable

data class TripVehicle(
    val trip: Trip,
    val vehicle: Vehicle
):Serializable
