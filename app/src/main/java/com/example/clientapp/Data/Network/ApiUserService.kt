package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Model.Request.LoginRequest
import com.example.clientapp.Domain.Model.Response.LoginResponse
import com.example.clientapp.Domain.Model.Response.RegisterResponse
import com.example.clientapp.Domain.Model.Model.User
import com.example.clientapp.Domain.Model.Response.TokenRespose
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiUserService {
    @POST("api/register")
    suspend fun registerUser(@Body user: User): Response<RegisterResponse>

    @POST("api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/validate")
    suspend fun validateUser(@Header("Authorization") token: String): Response<TokenRespose>
}