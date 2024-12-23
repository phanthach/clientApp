package com.example.clientapp.Domain.Model.Response

import com.example.clientapp.Domain.Model.Model.Location
import com.example.clientapp.Domain.Model.Model.Seat
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Model.Vehicle

data class TicketResponse(
    val ticket:Ticket?,
    val  trip: Trip?,
    val seat: Seat?,
    val pickupLocation: Location?,
    val dropLocation: Location?,
    val vehicle: Vehicle?
    )
