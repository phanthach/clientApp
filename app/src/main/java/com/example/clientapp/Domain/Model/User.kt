package com.example.clientapp.Domain.Model

data class User(
    val userId: Int? = null,
    val fullname: String,
    val birthDay: String,
    val email: String? = null,
    val password: String,
    val address: String,
    val phoneNumber: String,
    val roleId: Int,
    val createdAt: String,
    val isBlocked: Int,
    val licenseNumber: String? = null,
    val companyName: String? = null
)
