package com.example.clientapp.Presentation.SelectLocation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.compose.ui.text.toLowerCase
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity(), ItemListener {
    private lateinit var binding: ActivitySelectLocationBinding
    private lateinit var listLocation: List<String>
    private lateinit var filteredList:MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        listLocation = resources.getStringArray(R.array.provinces).toList()
        filteredList = mutableListOf()

        val checkLocation = intent.getIntExtra("checkLocation",0)
        val adapter = LocationRecycleView(this,listLocation)
        binding.recyclerViewLocation.adapter = adapter
        binding.recyclerViewLocation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if (checkLocation==1){
            binding.imagePoints.setImageResource(R.drawable.placeholder)
        }
        else{
            binding.imagePoints.setImageResource(R.drawable.points)
        }
        binding.btBack.setOnClickListener{
            finish()
        }
        inputLocation(adapter)
    }

    private fun inputLocation(adapter: LocationRecycleView) {
        binding.searchLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText, adapter)
                return true
            }
        })
    }
    fun filter(text:String, adapter: LocationRecycleView) {
        filteredList.clear()
        listLocation.forEach{ item ->
            if(item.lowercase().contains(text.lowercase())){
                filteredList.add(item)
            }
        }
        adapter.filterList(filteredList)
    }
    override fun onItemClick(position: Int) {
        val intent = Intent()
        if(filteredList.isEmpty()){
            intent.putExtra("location",listLocation[position])
        }
        else{
            intent.putExtra("location",filteredList[position])
        }
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
        TODO("Not yet implemented")
    }
}