package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Request.OrderRequest

interface PaymentRepository {
    suspend fun submitOrder(token: String, amount: Int, orderInfo: String, oderRequest: OrderRequest, modId: Int, modIdReturn: Int, roundTrip:Int, ticketPrice:Int,ticketPriceReturn:Int): Int
    suspend fun getPayment(token: String, paymentId: Int): Payment
}