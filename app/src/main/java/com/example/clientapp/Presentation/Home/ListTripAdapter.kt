package com.example.clientapp.Presentation.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemDanhSachBinding
import com.example.clientapp.databinding.ItemTripBinding


class ListTripAdapter(private val mList: MutableList<TripVehicle>, private val itemListener: ItemListener): RecyclerView.Adapter<ListTripAdapter.ViewHolder>() {
    private var selectedPosition: Int? = null
    inner class ViewHolder(private val binding: ItemTripBinding, private val itemListener: ItemListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: TripVehicle, position: Int){
            binding.tenNhaxe.text = item.vehicle.plateNumber
            if(item.trip.status == 3){
                binding.status.text = "Đã bắt đầu"
                binding.status.setTextColor(binding.root.context.resources.getColor(R.color.orange))
            }
            else{
                binding.status.text = "Chưa bắt đầu"
                binding.status.setTextColor(binding.root.context.resources.getColor(R.color.colorAccent))
            }
            Glide.with(binding.root)
                .load(item.vehicle.img)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(binding.imageView)
            binding.dateStart.text = item.trip.departureDate
            binding.timeStart.text = item.trip.departureTime
            binding.dateEnd.text = item.trip.arrivalDate
            binding.timeEnd.text = item.trip.arrivalTime
            binding.root.setOnClickListener {
                itemListener.onItemClick(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.bind(item, position)
    }
    fun updateList(newList: List<TripVehicle>){
        mList.clear()
        mList.addAll(newList)
        notifyDataSetChanged()
    }
}