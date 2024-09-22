package com.example.clientapp.Presentation.UI.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.Presentation.UI.Adapter.DanhSachAdapter
import com.example.clientapp.R

class DanhSach : AppCompatActivity() {
    private lateinit var adapter: DanhSachAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_sach)
        val list = listOf("Thọ Lam", "Hải Nam", "Tùng Nhường", "Mận Vũ", "Hoài Giang")
        adapter = DanhSachAdapter(list)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}