package com.example.clientapp.Presentation.VehicleDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.LayoutSeat
import com.example.clientapp.Domain.Model.Model.Seat
import com.example.clientapp.Domain.Repository.ItemSelectSeat
import com.example.clientapp.R
import com.example.clientapp.databinding.ItemLayoutSeatBinding

class SelectSeatRecycleViewAdapter(
    private var mListSeat: LayoutSeat?,
    private val itemSelectSeat: ItemSelectSeat
) : RecyclerView.Adapter<SelectSeatRecycleViewAdapter.ViewHolder>() {
    private var totalSeat = 0
    inner class ViewHolder(private val binding: ItemLayoutSeatBinding, private val itemSelectSeat: ItemSelectSeat) : RecyclerView.ViewHolder(binding.root) {
        fun bind(colums: Int, rows: Int,item: List<Seat>,item2:List<Seat>, position: Int) {
            binding.txtFloor.text = (position+1).toString()
            binding.gridLayout.columnCount = colums
            binding.gridLayout.rowCount = rows

            // Clear any existing views
            binding.gridLayout.removeAllViews()

            // Add ImageView items to the GridLayout
            for (seat in item) {
                val imageView = ImageView(binding.root.context).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0 // Let GridLayout divide width evenly
                        height = 0 // Let GridLayout divide height evenly
                        rowSpec = GridLayout.spec(seat.positionY-1, 1f)
                        columnSpec = GridLayout.spec(seat.positionX-1, 1f)
                        setMargins(8, 8, 8, 8) // Add margin around each ImageView
                    }
                    scaleType = ImageView.ScaleType.FIT_CENTER // Crop image to fit cell
                    setImageResource(R.drawable.chairtrue) // Set placeholder image
                    tag = false
                }
                binding.gridLayout.addView(imageView)
                imageView.setOnClickListener{
                    val isSelect = imageView.tag as Boolean
                    if(totalSeat<5){
                        if(isSelect) {
                            imageView.setImageResource(R.drawable.chairtrue)
                            imageView.tag = false
                            itemSelectSeat.onItemSelect(false, seat.nameSeat, seat.seatId, position)
                            totalSeat-=1
                        }
                        else{
                            imageView.setImageResource(R.drawable.armchairfalse)
                            imageView.tag = true
                            itemSelectSeat.onItemSelect(true, seat.nameSeat, seat.seatId, position)
                            totalSeat+=1
                        }
                    }
                    else{
                        if(isSelect) {
                            imageView.setImageResource(R.drawable.chairtrue)
                            imageView.tag = false
                            itemSelectSeat.onItemSelect(false, seat.nameSeat, seat.seatId, position)
                            totalSeat-=1
                        }
                        else{
                            Toast.makeText(binding.root.context, "Bạn chỉ được chọn tối đa 5 ghế", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            for (seat in item2) {
                val imageView = ImageView(binding.root.context).apply {
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0 // Let GridLayout divide width evenly
                        height = 0 // Let GridLayout divide height evenly
                        rowSpec = GridLayout.spec(seat.positionY-1, 1f)
                        columnSpec = GridLayout.spec(seat.positionX-1, 1f)
                        setMargins(8, 8, 8, 8) // Add margin around each ImageView
                    }
                    scaleType = ImageView.ScaleType.FIT_CENTER // Crop image to fit cell
                    setImageResource(R.drawable.armchairfalse) // Set placeholder image
                    tag = false
                }
                binding.gridLayout.addView(imageView)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemSelectSeat)
    }

    override fun getItemCount(): Int {
        if(mListSeat == null) return 0
        return mListSeat!!.floor!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(mListSeat == null) return
        val listSeat = mListSeat!!.listSeat!!.filter { it.floor == position+1 }
        val seatsBooked = mListSeat!!.seatsBooked!!.filter { it.floor == position+1 }
        holder.bind(mListSeat!!.x!!, mListSeat!!.y!!,listSeat, seatsBooked, position)
    }
    fun updateData(newList: LayoutSeat?) {
        mListSeat = newList!!
        notifyDataSetChanged()
    }
}