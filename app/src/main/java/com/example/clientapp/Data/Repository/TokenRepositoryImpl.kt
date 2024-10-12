package com.example.clientapp.Data.Repository

import android.content.SharedPreferences
import com.example.clientapp.Domain.Repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor( private val sharedPreferences: SharedPreferences): TokenRepository {
    companion object{
        private const val TOKEN_KEY = "tokenKey"
    }
    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("tokenKey", token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("tokenKey", null)
    }

    override fun deleteToken() {
        sharedPreferences.edit().remove("tokenKey").apply()
    }
}