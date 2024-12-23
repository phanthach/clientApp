package com.example.clientapp.Data.Repository

import com.example.clientapp.Data.Network.ApiTicketService
import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import com.example.clientapp.Domain.Repository.TicketRepository
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(private val apiTicketService: ApiTicketService): TicketRepository {
    override suspend fun getListTicket(token: String,paymentId: Int): List<TicketDetailResponse> {
        return try {
            val response = apiTicketService.getListTicket(token, paymentId)
            when(response.code()){
                200 -> response.body()!!
                401 -> throw Exception("Người dùng không hợp lệ")
                else -> throw Exception("Lỗi không xác định")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf(TicketDetailResponse(null,null,null,null))
        }
    }

    override suspend fun getTicketDetail(token: String, ticketId: Int): TicketResponse {
        return try {
            val response = apiTicketService.getTicketDetail(token, ticketId)
            when(response.code()){
                200 -> response.body()!!
                401 -> throw Exception("Người dùng không hợp lệ")
                else -> throw Exception("Lỗi không xác định")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return TicketResponse(null,null,null,null,null,null)
        }
    }
}