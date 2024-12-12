package com.example.clientapp.Domain.Repository

import com.example.clientapp.Domain.Model.Request.LoginRequest
import com.example.clientapp.Domain.Model.Response.LoginResponse
import com.example.clientapp.Domain.Model.Response.RegisterResponse
import com.example.clientapp.Domain.Model.Model.User
import com.example.clientapp.Domain.Model.Response.TokenRespose

interface UserRepository {
    suspend fun registerUser(user: User): RegisterResponse
    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse
    suspend fun validateUser(token: String): TokenRespose
}