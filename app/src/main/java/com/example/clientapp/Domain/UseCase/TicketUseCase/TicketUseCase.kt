package com.example.clientapp.Domain.UseCase.TicketUseCase

import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import com.example.clientapp.Domain.Repository.TicketRepository
import com.example.clientapp.Domain.Repository.TokenRepository
import javax.inject.Inject

class TicketUseCase @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val tokenRepository: TokenRepository
) {
    suspend fun getListTicket(paymentId: Int): List<TicketDetailResponse> {
        val token ="Bearer " + tokenRepository.getToken()
        return ticketRepository.getListTicket(token, paymentId)
    }
    suspend fun getTicketDetail(ticketId: Int): TicketResponse {
        val token ="Bearer " + tokenRepository.getToken()
        return ticketRepository.getTicketDetail(token, ticketId)
    }
}