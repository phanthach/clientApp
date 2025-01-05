package com.example.clientapp.Presentation.TripDetailDriver

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.clientapp.Presentation.CustomerTrip.FragmentCustomerTrip
import com.example.clientapp.Presentation.MapTrip.FragmentMapTrip
import com.example.clientapp.Presentation.PointTrip.FragmentPointTrip

class TabTripPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return FragmentMapTrip()
            1 -> return FragmentCustomerTrip()
            else -> return FragmentMapTrip()
        }
    }
}
