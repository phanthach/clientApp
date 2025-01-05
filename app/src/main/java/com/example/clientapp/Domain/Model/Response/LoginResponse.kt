package com.example.clientapp.Domain.Model.Response

data class LoginResponse(
    val message: String,
    val status: Int,
    val roleId: Int?,
    val token: String?,
    val userId: Int?,
    val fullName: String?
)