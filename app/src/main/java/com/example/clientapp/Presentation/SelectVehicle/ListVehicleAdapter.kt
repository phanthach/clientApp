package com.example.clientapp.Presentation.SelectVehicle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemDanhSachBinding


class ListVehicleAdapter(private val mList: MutableList<TripVehicle>, private val itemListener: ItemListener): RecyclerView.Adapter<ListVehicleAdapter.ViewHolder>() {
    private var selectedPosition: Int? = null
    inner class ViewHolder(private val binding:ItemDanhSachBinding, private val itemListener: ItemListener) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: TripVehicle, position: Int){
            binding.tenNhaxe.text = item.trip.nameVehicle
            Glide.with(binding.root)
                .load(item.vehicle.img)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(binding.imageView)
            binding.vehicleType.text = item.vehicle.vehicleType
            binding.dateStart.text = item.trip.departureDate
            binding.timeStart.text = item.trip.departureTime
            binding.dateEnd.text = item.trip.arrivalDate
            binding.timeEnd.text = item.trip.arrivalTime
            binding.priceTicket.text = item.trip.ticketPrice.toString()
            if(selectedPosition == position){
                binding.cardView.setBackgroundResource(R.drawable.unradius)
                binding.cardView2.setBackgroundResource(R.drawable.unradius)
            }
            else{
                binding.cardView.setBackgroundResource(R.drawable.radius)
                binding.cardView2.setBackgroundResource(R.drawable.radius)
            }
            binding.root.setOnClickListener {
                if (selectedPosition == position) {
                    // Bỏ chọn nếu item đã được chọn trước đó
                    selectedPosition = null
                    itemListener.onItemClickSelected(position, false)
                } else {
                    // Chọn item mới
                    selectedPosition = position
                    itemListener.onItemClickSelected(position, true)
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDanhSachBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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