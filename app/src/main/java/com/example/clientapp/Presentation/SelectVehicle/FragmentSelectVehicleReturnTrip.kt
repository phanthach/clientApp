package com.example.clientapp.Presentation.SelectVehicle

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.Home.FragmentHomeViewModel
import com.example.clientapp.databinding.FragmentSelectVehicleBinding
import com.example.clientapp.databinding.FragmentSelectVehicleReturnTripBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSelectVehicleReturnTrip: Fragment(), ItemListener {
    private var _binding: FragmentSelectVehicleReturnTripBinding? = null
    private val binding get() = _binding!!
    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()
    private val fragmentSelectVehicleViewModel: FragmentSelectVehicleViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectVehicleReturnTripBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListVehicleAdapter(mutableListOf(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.btBack.setOnClickListener { requireActivity().onBackPressed() }

        val pickUpPoint = activity?.intent?.getStringExtra("pickUpPoint")
        val dropOffPoint = activity?.intent?.getStringExtra("dropOffPoint")
        val departureDate = activity?.intent?.getStringExtra("departureDate")
        val returnDate = activity?.intent?.getStringExtra("returnDate")
        val roundTrip = activity?.intent?.getBooleanExtra("roundTrip", false) ?: false

        fragmentHomeViewModel.setPickOffPoint(pickUpPoint ?: "")
        fragmentHomeViewModel.setDropOffPoint(dropOffPoint ?: "")
        fragmentHomeViewModel.setDepartureDate(departureDate ?: "")
        fragmentHomeViewModel.setReturnDate(returnDate ?: "")
        fragmentHomeViewModel.setRoundTrip(roundTrip)
        setUpInfo()
    }

    private fun setUpInfo() {
        fragmentHomeViewModel.pickUpPoint.observe(viewLifecycleOwner, Observer {
            binding.tvTitleAddress.text = it
        })
        fragmentHomeViewModel.dropOffPoint.observe(viewLifecycleOwner, Observer {
            binding.tvTitleAddress2.text = it
        })

        fragmentHomeViewModel.roundTrip.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.dateBack.visibility = View.VISIBLE
                binding.tvTitleDate2.visibility = View.VISIBLE
            }else{

            }
        })
        fragmentHomeViewModel.departureDate.observe(viewLifecycleOwner, Observer {
            binding.dateStart.text = it
        })
        fragmentHomeViewModel.returnDate.observe(viewLifecycleOwner, Observer {
            binding.dateBack.text = it
        })
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
        if(isSelected==true){
            binding.tvCount.text ="Đã chọn : 2 chuyến"
        }
        else{
            binding.tvCount.text ="Đã chọn : 1 chuyến"
        }
    }
}