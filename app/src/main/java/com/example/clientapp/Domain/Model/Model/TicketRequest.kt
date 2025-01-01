package com.example.clientapp.Domain.Model.Model

data class TicketRequest(
    val tripId: Int,
    val seatId: Int,
    val pickupPointId: Int,
    val dropPointId: Int
)
