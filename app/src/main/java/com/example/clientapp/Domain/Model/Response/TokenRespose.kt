package com.example.clientapp.Domain.Model.Response

data class TokenRespose(
    val status: Int,
    val message: String,
    val fullName: String,
    val roleId: Int?,
    val userId: Int?
)