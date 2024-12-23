package com.example.clientapp.Presentation.Present

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.DetailTicket.DetailTicketActivity
import com.example.clientapp.Presentation.SelectVehicle.PaymentPresentAdapter
import com.example.clientapp.databinding.FragmentPresentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPresent: Fragment(), ItemListener {
    private var _binding: FragmentPresentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentPresentAdapter
    private val fragmentPresentViewModel: FragmentPresentViewModel by viewModels()
    private var listPayment = mutableListOf<Payment>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPresentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PaymentPresentAdapter(mutableListOf(), this)
        binding.rvHistory.adapter = adapter
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.progressBar.visibility = View.VISIBLE
        getListPayment()
    }

    private fun getListPayment() {
        fragmentPresentViewModel.listPayment()
        fragmentPresentViewModel.listPayment.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            if(it.isEmpty() || it == null){
                binding.tvEmpty.visibility = View.VISIBLE
            }
            else{
                binding.tvEmpty.visibility = View.GONE
                listPayment.addAll(it)
                adapter.updateList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getListPayment()
    }
    override fun onItemClick(position: Int) {
        val payment = listPayment[position]
        val intent = Intent(requireContext(), DetailTicketActivity::class.java)
        intent.putExtra("payment", payment)
        startActivity(intent)
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
        TODO("Not yet implemented")
    }
}