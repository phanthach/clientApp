package com.example.clientapp.Presentation.SelectVehicle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemDanhSachBinding
import com.example.clientapp.databinding.ItemHistoryBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PaymentPresentAdapter(private val mList: MutableList<Payment>, private val itemListener: ItemListener): RecyclerView.Adapter<PaymentPresentAdapter.ViewHolder>() {
    private var selectedPosition: Int? = null

    inner class ViewHolder(private val binding:ItemHistoryBinding, private val itemListener: ItemListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Payment, position: Int){
            binding.tvTransactionID.text = item.transactionId
            binding.tvPrice.text = item.amount.toString()
            val time = convertTimeModern(item.cancelTime)
            binding.tvCancleTime.text = time
            binding.root.setOnClickListener{
                itemListener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.bind(item, position)
    }
    fun updateList(newList: List<Payment>){
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