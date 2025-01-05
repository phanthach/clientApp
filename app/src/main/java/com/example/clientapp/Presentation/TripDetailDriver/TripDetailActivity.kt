package com.example.clientapp.Presentation.TripDetailDriver

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityTripDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTripDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTabLayoutAndViewPager()
    }
    private fun setUpTabLayoutAndViewPager() {
        val tabPagerAdapter = TabTripPagerAdapter(this)
        binding.viewPager.adapter = tabPagerAdapter
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Map"
                1 -> "Khách hàng"
                else -> "Map"
            }
        }.attach()
    }
}