package com.example.clientapp.Presentation.UI.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.R


class DanhSachAdapter(private val mList: List<String>): RecyclerView.Adapter<DanhSachAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView: TextView
        var mota:TextView
        init {
            textView = itemView.findViewById(R.id.tenNhaxe)
            mota = itemView.findViewById(R.id.moTa)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danh_sach, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.textView.text=item
    }

}