package com.example.clientapp.Domain.UseCase.LayoutUseCase

import com.example.clientapp.Data.Repository.LayoutRepositoryImpl
import com.example.clientapp.Data.Repository.TokenRepositoryImpl
import com.example.clientapp.Domain.Model.Model.LayoutSeat
import com.example.clientapp.Domain.Repository.LayoutRepository
import com.example.clientapp.Domain.Repository.TokenRepository
import jakarta.inject.Inject
import com.example.clientapp.Domain.Model.Model.Result

class GetLayoutUseCase @Inject constructor(
    private val layoutRepository: LayoutRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun getLayout(layoutId: Int): LayoutSeat {
        val token ="Bearer " + tokenRepository.getToken()
        return layoutRepository.getLayout(token, layoutId)
    }
}