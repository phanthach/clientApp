package com.example.clientapp.Domain.UseCase.PaymentUseCase

import android.util.Log
import com.example.clientapp.Domain.Model.Model.Payment
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
    suspend fun countPayment(): Long {
        val token ="Bearer " + tokenRepository.getToken()
        return paymentRepository.countPayment(token)
    }
    suspend fun listPayment(): List<Payment> {
        val token ="Bearer " + tokenRepository.getToken()
        return paymentRepository.listPayment(token)
    }
    suspend fun listPaymentGone(): List<Payment> {
        val token ="Bearer " + tokenRepository.getToken()
        Log.e("TokenCheck", "TokenA: $token")
        return paymentRepository.listPaymentGone(token)
    }
    suspend fun listPaymentCancel(): List<Payment> {
        val token ="Bearer " + tokenRepository.getToken()
        return paymentRepository.listPaymentCancel(token)
    }
    suspend fun updatePaymentStatus(paymentId: Int, status: Int): Payment {
        val token ="Bearer " + tokenRepository.getToken()
         return paymentRepository.updatePaymentStatus(token, paymentId, status)
    }

}