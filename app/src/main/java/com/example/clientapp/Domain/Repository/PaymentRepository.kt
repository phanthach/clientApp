package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Request.OrderRequest

interface PaymentRepository {
    suspend fun submitOrder(token: String, amount: Int, orderInfo: String, oderRequest: OrderRequest, modId: Int, modIdReturn: Int, roundTrip:Int, ticketPrice:Int,ticketPriceReturn:Int): Int
    suspend fun getPayment(token: String, paymentId: Int): Payment
    suspend fun countPayment(token: String): Long
    suspend fun listPayment(token: String): List<Payment>
    suspend fun listPaymentGone(token: String): List<Payment>
    suspend fun listPaymentCancel(token: String): List<Payment>
    suspend fun updatePaymentStatus(token: String, paymentId: Int, status: Int): Payment
}