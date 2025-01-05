package com.example.clientapp.Data.Repository

import com.example.clientapp.Data.Network.ApiUserService
import com.example.clientapp.Domain.Model.Request.LoginRequest
import com.example.clientapp.Domain.Model.Response.LoginResponse
import com.example.clientapp.Domain.Model.Response.RegisterResponse
import com.example.clientapp.Domain.Model.Model.User
import com.example.clientapp.Domain.Model.Response.TokenRespose
import com.example.clientapp.Domain.Repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiUserService): UserRepository {
    override suspend fun registerUser(user: User): RegisterResponse {
        return try {
            apiService.registerUser(user).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            RegisterResponse("An error occurred", 0)
        }
    }
    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return try {
            apiService.loginUser(loginRequest).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            LoginResponse("An error occurred", 0, null, null, null, null)
        }
    }

    override suspend fun validateUser(token: String): TokenRespose {
        return try {
            apiService.validateUser(token).body()!!
        } catch (e: Exception) {
            e.printStackTrace()
            TokenRespose(0, "Error", "Error", null, null)
        }
    }

    override suspend fun saveFCMToken(tokenA: String, token: String): Boolean {
        return try {
            apiService.saveFCMToken(tokenA, token).isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}