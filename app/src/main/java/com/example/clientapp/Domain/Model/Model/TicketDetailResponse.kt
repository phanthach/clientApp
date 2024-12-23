package com.example.clientapp.Domain.Model.Model

data class TicketDetailResponse(
    val trip: Trip?,
    val ticket: Ticket?,
    val pickupPoint: Location?,
    val dropPoint: Location?,
)
