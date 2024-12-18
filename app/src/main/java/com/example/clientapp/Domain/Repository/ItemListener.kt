package com.example.clientapp.Domain.Repository

interface ItemListener {
    fun onItemClick(position: Int)
    fun onItemClickSelected(position: Int, isSelected: Boolean)
}