package com.example.clientapp.Domain.Repository

interface TokenRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun deleteToken()
}