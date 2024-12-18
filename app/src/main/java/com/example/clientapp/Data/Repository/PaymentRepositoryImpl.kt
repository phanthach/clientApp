package com.example.clientapp.Data.Repository

import android.util.Log
import com.example.clientapp.Data.Network.ApiPaymentService
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Request.OrderRequest
import com.example.clientapp.Domain.Repository.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val apiPaymentService: ApiPaymentService): PaymentRepository {
    override suspend fun submitOrder(token: String, amount: Int, orderInfo: String, oderRequest: OrderRequest, modId: Int, modIdReturn: Int, roundTrip:Int, ticketPrice:Int,ticketPriceReturn:Int): Int {
        Log.d("PaymentRepositoryImpl", "submitOrder")
        return try {
            apiPaymentService.submitOrder(token, amount, orderInfo,oderRequest, modId,modIdReturn, roundTrip, ticketPrice,ticketPriceReturn)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("PaymentRepositoryImpl", "error", e)
            -1
        }
    }

    override suspend fun getPayment(token: String, paymentId: Int): Payment {
        return try {
            val response = apiPaymentService.getPayment(token, paymentId)
            when(response.code()){
                200 -> response.body()!!
                401 -> throw Exception("Người dùng không hợp lệ")
                404 -> throw Exception("Thanh toán đã hết hạn")
                else -> throw Exception("Lỗi không xác định")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Payment(-1, -1, "", 0, "", "", -1, "",null,"")
        }
    }
}