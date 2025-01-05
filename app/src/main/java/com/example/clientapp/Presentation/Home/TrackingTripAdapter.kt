package com.example.clientapp.Presentation.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemDanhSachBinding
import com.example.clientapp.databinding.ItemNotiTripBinding
import com.example.clientapp.databinding.ItemTripBinding


class TrackingTripAdapter(private val mList: MutableList<Trip>, private val itemListener: ItemListener): RecyclerView.Adapter<TrackingTripAdapter.ViewHolder>() {
    private var selectedPosition: Int? = null
    inner class ViewHolder(private val binding: ItemNotiTripBinding, private val itemListener: ItemListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Trip, position: Int){
            binding.tenNhaxe.text = item.nameVehicle
            binding.dateStart.text = item.departureDate
            binding.timeStart.text = item.departureTime
            binding.dateEnd.text = item.arrivalDate
            binding.timeEnd.text = item.arrivalTime
            binding.root.setOnClickListener {
                itemListener.onItemClick(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNotiTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.bind(item, position)
    }
    fun updateList(newList: List<Trip>){
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }
}