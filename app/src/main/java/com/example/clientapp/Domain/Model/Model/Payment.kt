package com.example.clientapp.Domain.Model.Model

data class Payment(

    val paymentId: Int,
    val userId: Int,
    val paymentMethod: String,
    val paymentStatus: Int,
    val paymentTime: String,
    val transactionId: String,
    val amount: Int,
    val paymentType: String,
    val paymentUrl:String?,
    val cancelTime:String
)
