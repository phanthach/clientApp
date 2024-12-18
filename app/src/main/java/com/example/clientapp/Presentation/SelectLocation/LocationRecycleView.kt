package com.example.clientapp.Presentation.SelectLocation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.databinding.ItemLocationBinding


class LocationRecycleView(private val itemListener: ItemListener,private var mList: List<String>) : RecyclerView.Adapter<LocationRecycleView.ViewHolder>() {
    class ViewHolder(private val binding: ItemLocationBinding, private val itemListener: ItemListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.location.text = item
            binding.root.setOnClickListener{
                itemListener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemListener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position],position)
    }
    fun filterList(filteredList: List<String>) {
        mList = filteredList
        notifyDataSetChanged()
    }
}