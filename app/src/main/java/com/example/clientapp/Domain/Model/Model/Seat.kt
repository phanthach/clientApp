package com.example.clientapp.Domain.Model.Model

data class Seat(
    val seatId: Int,
    val layoutId: Int,
    val nameSeat: String,
    val positionX: Int,
    val positionY: Int,
    val floor: Int
)
