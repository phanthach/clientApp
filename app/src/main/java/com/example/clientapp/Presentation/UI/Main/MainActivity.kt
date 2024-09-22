package com.example.clientapp.Presentation.UI.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.clientapp.Presentation.UI.Adapter.ViewPagerAdapter
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewPager()
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    binding.viewpager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.history -> {
                    binding.viewpager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.scanQR -> {
                    binding.viewpager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.account -> {
                    binding.viewpager.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    binding.viewpager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }
    }

    private fun setUpViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.viewpager.adapter = viewPagerAdapter
        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> binding.bottomNavigation.getMenu().findItem(R.id.home).setChecked(true)
                    1 -> binding.bottomNavigation.getMenu().findItem(R.id.history).setChecked(true)
                    2 -> binding.bottomNavigation.getMenu().findItem(R.id.scanQR).setChecked(true)
                    else -> binding.bottomNavigation.getMenu().findItem(R.id.account).setChecked(true)
                }
            }
            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}