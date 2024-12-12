package com.example.clientapp.Presentation.VehicleDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.Domain.Model.Model.ListLocation
import com.example.clientapp.Domain.Model.Model.Location
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.databinding.ItemLocationBinding
import com.example.clientapp.databinding.ItemPickLocationBinding

class SelectLocationAdapter(private var listLocation:MutableList<ListLocation>, private val itemListener: ItemListener):RecyclerView.Adapter<SelectLocationAdapter.ViewHolder>() {
    private var selectedPosition: Int? = null
    inner class ViewHolder(private val binding: ItemPickLocationBinding, private val itemListener: ItemListener):RecyclerView.ViewHolder(binding.root) {
        fun bind(location: String, distance: String, position: Int) {
            binding.location.text = location
            binding.distance.text = distance
            if(selectedPosition == position){
                binding.choose.visibility = ViewGroup.VISIBLE
            }
            else{
                binding.choose.visibility = ViewGroup.GONE
            }
            binding.choose.setOnClickListener{
                itemListener.onItemClick(selectedPosition!!)
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
        val binding = ItemPickLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return listLocation.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (listLocation.isNotEmpty()){
            holder.bind(listLocation[position].location.nameLocation!!, listLocation[position].distance, position)
        }
    }
    fun updateData(newList: List<ListLocation>){
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