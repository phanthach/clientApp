package com.example.clientapp.Presentation.SelectVehicle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemDanhSachBinding
import com.example.clientapp.databinding.ItemHistoryBinding
import com.example.clientapp.databinding.ItemTicketBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ListTicketAdapter(private val mList: MutableList<TicketDetailResponse>, private val itemListener: ItemListener): RecyclerView.Adapter<ListTicketAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding:ItemTicketBinding, private val itemListener: ItemListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: TicketDetailResponse, position: Int){
            binding.tvNameVehicle1.text = item.trip!!.nameVehicle
            binding.tvTransactionID.text = item.ticket!!.ticketCode
            binding.diemDon1.text = item.pickupPoint!!.nameLocation
            binding.diemTra1.text = item.dropPoint!!.nameLocation
            binding.tvPrice.text = item.ticket!!.ticketPrice.toString()
            binding.root.setOnClickListener {
                itemListener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.bind(item, position)
    }
    fun updateList(newList: List<TicketDetailResponse>){
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }

    fun convertTimeModern(inputTime: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val outputFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")

        val dateTime = LocalDateTime.parse(inputTime, inputFormatter)
        return dateTime.format(outputFormatter)
    }
}