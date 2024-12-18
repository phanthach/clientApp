package com.example.clientapp.Domain.Model.Request

import com.example.clientapp.Domain.Model.Model.Ticket

data class OrderRequest (
    val tickets: List<Ticket>,
    val ticketsReturn: List<Ticket>?
)