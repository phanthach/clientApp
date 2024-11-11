package com.example.clientapp.Presentation.VehicleDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Presentation.BookTicket.BookTicketActivityViewModel
import com.example.clientapp.Presentation.SelectVehicle.ListVehicleAdapter
import com.example.clientapp.databinding.FragmentSelectSeatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSelectSeat: Fragment() {
    private var _binding: FragmentSelectSeatBinding? = null
    private val bookTicketActivityViewModel: BookTicketActivityViewModel by activityViewModels()
    private val binding get() = _binding!!
    private lateinit var adapter: SelectSeatRecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectSeatBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SelectSeatRecycleViewAdapter()
        binding.rvSeat.adapter = adapter
        binding.rvSeat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        setUpInfo()
    }

    private fun setUpInfo() {
        bookTicketActivityViewModel.trips.observe(viewLifecycleOwner, {
            binding.tvTotalSeat.text = it.trip.nameVehicle
        })
    }
}