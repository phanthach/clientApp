package com.example.clientapp.Domain.Model.Model

import java.io.Serializable

data class Trip(
//"tripId": 2, "modId": 1,"routeId": 9,"driverId": 2,"vehicleId": 3,"departureTime": "21:00","departureDate": "04/11/2024","arrivalTime": "04:00","arrivalDate": "05/11/2024","availableSeats": 46, "ticketPrice": 250000, "status": "1"
    val tripId: Int,
    val modId: Int,
    val routeId: Int,
    val driverId: Int,
    val vehicleId: Int,
    val departureTime: String,
    val departureDate: String,
    val arrivalTime: String,
    val arrivalDate: String,
    val availableSeats: Int,
    val ticketPrice: Int,
    val status: Int,
    val nameVehicle: String
): Serializable
