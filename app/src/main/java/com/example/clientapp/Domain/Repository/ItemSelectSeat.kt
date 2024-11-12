package com.example.clientapp.Domain.Repository

interface ItemSelectSeat {
    fun onItemSelect(isSelect: Boolean,nameSeat: String, seatId: Int,position: Int)
}