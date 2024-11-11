package com.example.clientapp.Presentation.VehicleDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemLayoutSeatBinding

class SelectSeatRecycleViewAdapter : RecyclerView.Adapter<SelectSeatRecycleViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutSeatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.txtFloor.text = position.toString()
            binding.gridLayout.columnCount = 5
            binding.gridLayout.rowCount = 10

            // Clear any existing views
            binding.gridLayout.removeAllViews()

            // Add ImageView items to the GridLayout
            for (i in 0 until (binding.gridLayout.columnCount * binding.gridLayout.rowCount)) {
                val imageView = ImageView(binding.root.context)
                binding.gridLayout.addView(imageView)

                // Load image into ImageView
                Glide.with(binding.root.context)
                    .load(R.drawable.logo) // Replace with your image URL or resource
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind("", position)
    }
}