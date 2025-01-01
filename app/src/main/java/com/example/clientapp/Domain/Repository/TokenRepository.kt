package com.example.clientapp.Domain.Repository

interface TokenRepository {
    fun saveToken(token: String, remember: Boolean)
    fun getToken(): String?
    fun deleteToken()
    fun getTokenValid():String?
    fun saveFCMToken(token: String)
    fun getFCMToken():String?
}