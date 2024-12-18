package com.example.clientapp.Domain.UseCase.PaymentUseCase

import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Request.OrderRequest
import com.example.clientapp.Domain.Repository.PaymentRepository
import com.example.clientapp.Domain.Repository.TokenRepository
import javax.inject.Inject

class PaymentUseCase @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun submitOrder(amount: Int, orderInfo: String, oderRequest: OrderRequest, modId: Int, modIdReturn: Int, roundTrip:Int, ticketPrice:Int, ticketPriceReturn:Int): Int {
        val token ="Bearer " + tokenRepository.getToken()
        return paymentRepository.submitOrder(token, amount, orderInfo, oderRequest, modId,modIdReturn, roundTrip, ticketPrice,ticketPriceReturn)
    }
    suspend fun getPayment(paymentId: Int):Payment {
        val token ="Bearer " + tokenRepository.getToken()
        return paymentRepository.getPayment(token, paymentId)
    }
}