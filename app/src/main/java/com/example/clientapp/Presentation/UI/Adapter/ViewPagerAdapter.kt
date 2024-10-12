package com.example.clientapp.Presentation.UI.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.clientapp.Presentation.UI.Account.FragmentAccount
import com.example.clientapp.Presentation.UI.History.FragmentHistory
import com.example.clientapp.Presentation.UI.Home.FragmentHome
import com.example.clientapp.Presentation.UI.ScanQR.FragmentScanQR

class ViewPagerAdapter(fm: FragmentManager, behavior: Int): FragmentStatePagerAdapter(fm, behavior) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FragmentHome()
            1 -> return FragmentHistory()
            2 -> return FragmentScanQR()
            3 -> return FragmentAccount()
            else -> return FragmentHome()
        }
    }
}