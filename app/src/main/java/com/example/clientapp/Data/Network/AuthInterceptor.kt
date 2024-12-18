package com.example.clientapp.Data.Network

import com.example.clientapp.Domain.Repository.TokenRepository
import com.google.firebase.database.core.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenRepository: TokenRepository): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenRepository.getToken()

        val newRequest = if (token != null) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}