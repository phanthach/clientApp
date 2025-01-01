package com.example.clientapp.Domain.Model.Request

import com.example.clientapp.Domain.Model.Model.TicketRequest

data class OrderRequest (
    val tickets: List<TicketRequest>,
    val ticketsReturn: List<TicketRequest>?
)