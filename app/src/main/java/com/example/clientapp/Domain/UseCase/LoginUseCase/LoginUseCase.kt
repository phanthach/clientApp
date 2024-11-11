package com.example.clientapp.Domain.UseCase.LoginUseCase

import com.example.clientapp.Domain.Model.Request.LoginRequest
import com.example.clientapp.Domain.Model.Response.LoginResponse
import com.example.clientapp.Domain.Repository.TokenRepository
import com.example.clientapp.Domain.Repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
) {
    suspend fun checkLogin(phoneNumber: String, password: String ): LoginResponse {
        val loginRequest = LoginRequest(phoneNumber, password)
        val response = userRepository.loginUser(loginRequest)
        if(response.status == 1){
            return response
        }else{
            return response
        }
    }
    fun deleteToken(){
        tokenRepository.deleteToken()
    }
    fun getToken(): String? {
        return tokenRepository.getToken()
    }
    fun saveToken(token: String, isRemember: Boolean){
        tokenRepository.saveToken(token, isRemember)
    }
}