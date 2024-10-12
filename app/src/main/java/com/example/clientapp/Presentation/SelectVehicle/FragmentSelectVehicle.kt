package com.example.clientapp.Presentation.SelectVehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.databinding.FragmentSelectVehicleBinding

class FragmentSelectVehicle: Fragment() {
    private var _binding: FragmentSelectVehicleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectVehicleBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListVehicleAdapter(listOf("Vehicle 1", "Vehicle 2", "Vehicle 3","Vehicle 1", "Vehicle 2", "Vehicle 3"))
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.btBack.setOnClickListener { requireActivity().onBackPressed() }
    }
}