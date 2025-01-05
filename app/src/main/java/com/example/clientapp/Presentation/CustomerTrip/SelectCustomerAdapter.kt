package com.example.clientapp.Presentation.CustomerTrip

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.Domain.Model.Model.Location
import com.example.clientapp.Domain.Model.Response.TicketAllResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemCustomerBinding
import com.example.clientapp.databinding.ItemPickLocationBinding

class SelectCustomerAdapter(private var listLocation:MutableList<TicketAllResponse>, private val itemListener: ItemListener):RecyclerView.Adapter<SelectCustomerAdapter.ViewHolder>() {
    private var selectedPosition: Int? = null
    inner class ViewHolder(private val binding: ItemCustomerBinding, private val itemListener: ItemListener):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketAllResponse, position: Int){
            binding.timeStart.text = item.pickupLocation!!.nameLocation
            binding.timeEnd.text = item.dropLocation!!.nameLocation
            binding.tenKH.text = item.fullName
            binding.phoneNumber.text = item.phoneNumber
            binding.ticketCode.text = item.ticket!!.ticketCode
            binding.numberSeat.text = item.seat!!.nameSeat
            if(item.ticket!!.status==2){
                binding.cardView.setBackgroundResource(R.drawable.boder_custom_a)
                binding.status.text = "Chưa đón"
                binding.status.setTextColor(binding.root.resources.getColor(R.color.orange))
            }
            else{
                binding.cardView.setBackgroundResource(R.drawable.boder_custom_b)
                binding.status.text = "Đã đón"
                binding.status.setTextColor(binding.root.resources.getColor(R.color.colorAccent))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return listLocation.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listLocation.isNotEmpty()){
            holder.bind(listLocation[position], position)
        }
    }
    fun updateData(newList: List<TicketAllResponse>){
        Log.d("LocationAdapter", "Cập nhật dữ liệu: $newList")
        listLocation.clear()
        listLocation.addAll(newList)
        notifyDataSetChanged()
    }
    fun SelectedPosition(newSelectedPosition: Int?) {
        selectedPosition = newSelectedPosition
        notifyDataSetChanged()
    }
}