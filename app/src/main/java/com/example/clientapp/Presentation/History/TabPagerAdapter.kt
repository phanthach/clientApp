package com.example.clientapp.Presentation.History

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clientapp.Presentation.Account.FragmentAccount
import com.example.clientapp.Presentation.Home.FragmentHome
import com.example.clientapp.Presentation.Present.FragmentCanceled
import com.example.clientapp.Presentation.Present.FragmentGone
import com.example.clientapp.Presentation.Present.FragmentPresent
import com.example.clientapp.Presentation.ScanQR.FragmentScanQR

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> return FragmentPresent()
            1 -> return FragmentGone()
            2 -> return FragmentCanceled()
            else -> return FragmentPresent()
        }
    }
}