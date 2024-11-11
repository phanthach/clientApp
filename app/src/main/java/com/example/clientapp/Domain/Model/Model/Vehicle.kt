package com.example.clientapp.Domain.Model.Model

data class Vehicle(
    val vehicleId: Int,
    val plateNumber: String,
    val vehicleType: String,
    val modId: Int,
    val img: String,
    val layoutId: Int,
    val status: Int
)
