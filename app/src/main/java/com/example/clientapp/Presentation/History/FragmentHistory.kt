package com.example.clientapp.Presentation.History

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.clientapp.Presentation.UserActivity.ViewPagerAdapter
import com.example.clientapp.R
import com.example.clientapp.Service.MyForegroundService
import com.example.clientapp.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentHistory: Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabLayoutAndViewPager()
    }
    private fun setUpTabLayoutAndViewPager() {
        val tabPagerAdapter = TabPagerAdapter(requireActivity())
        binding.viewPager.adapter = tabPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Hiện tại"
                1 -> "Hoàn thành"
                2 -> "Đã hủy"
                else -> "Hiện tại"
            }
        }.attach()
    }
}