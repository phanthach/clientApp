package com.example.clientapp.Domain.UseCase.TicketUseCase

import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketAllResponse
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
    suspend fun sendNotification(tripId: Int, locationId: Int, locationType: Int): String {
        val token ="Bearer " + tokenRepository.getToken()
        return ticketRepository.sendNotification(token, tripId, locationId, locationType)
    }
    suspend fun getAllTicketByTripId(tripId: Int): List<TicketAllResponse> {
        val token ="Bearer " + tokenRepository.getToken()
        return ticketRepository.getAllTicketByTripId(token, tripId)
    }
    suspend fun updateTicket(ticketCode: String): String {
        val token ="Bearer " + tokenRepository.getToken()
        return ticketRepository.updateTicket(token, ticketCode)
    }
}