package com.example.clientapp.Domain.Model.Model

import java.io.Serializable


data class Ticket(
    val ticketId: Int,
    val ticketCode: String,
    val userId: Int,
    val tripId: Int,
    val seatId: Int,
    val pickupPointId: Int,
    val dropPointId: Int,
    val bookingTime: String,
    val cancelTime: String,
    val qrCode: String,
    val paymentId: Int,
    val status: Int,
    val modId: Int,
    val ticketPrice: Int
): Serializable

