package com.example.clientapp.Domain.Model.Model

data class LayoutSeat(
    val message: String,
    val status: Int,
    val layoutId: Int?,
    val seatCapacity: Int?,
    val x: Int?,
    val y: Int?,
    val floor: Int?,
    val listSeat: List<Seat>? = null
)
