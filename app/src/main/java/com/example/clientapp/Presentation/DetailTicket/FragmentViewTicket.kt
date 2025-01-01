package com.example.clientapp.Presentation.DetailTicket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Domain.Model.Model.TicketDetailResponse
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.Pay.PayActivity
import com.example.clientapp.Presentation.SelectVehicle.ListTicketAdapter
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentViewTicketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class FragmentViewTicket:Fragment(), ItemListener {
    private var _binding: FragmentViewTicketBinding? = null
    private val binding get() = _binding!!
    private val fragmentViewTicketViewModel: FragmentViewTicketViewModel by viewModels()
    private val detailTicketActivityViewModel: DetailTicketActivityViewModel by activityViewModels()
    private lateinit var adapter: ListTicketAdapter
    private var listTicket: MutableList<TicketDetailResponse> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewTicketBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListTicketAdapter(mutableListOf(), this)
        binding.rvTicket.adapter = adapter
        binding.rvTicket.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val intent = activity?.intent?.getSerializableExtra("payment")
        val payment:Payment = intent as Payment
        var checkPayment = payment.paymentStatus
        fragmentViewTicketViewModel.setPayment(payment)
        detailTicketActivityViewModel.setPayment(payment)
        binding.progressBar.visibility = View.VISIBLE
        getListTicket()
        binding.btnPayment.setOnClickListener {
            if(checkPayment == 0){
                binding.btnPayment.text = "Thanh toán"
                val intent = Intent(requireContext(), PayActivity::class.java)
                intent.putExtra("paymentId", payment.paymentId)
                startActivity(intent)
            }
            else if(checkPayment == 1){
                binding.btnPayment.text = "Đã sử dụng"
                fragmentViewTicketViewModel.updatePayment(payment.paymentId, 3)

            }
            else if(checkPayment == 3) {
                binding.btnPayment.text = "Đánh dấu đã sử dụng"
                fragmentViewTicketViewModel.updatePayment(payment.paymentId, 1)
            }
        }
    }

    private fun getListTicket() {
        detailTicketActivityViewModel.payment.observe(viewLifecycleOwner, {
            if(it!=null){
                if(it.paymentStatus==2){
                    binding.btnPayment.visibility = View.GONE
                }
                else if (it.paymentStatus==0){
                    binding.btnPayment.visibility = View.VISIBLE
                    binding.btnPayment.text = "Thanh toán"
                }
                else if(it.paymentStatus == 3){
                    binding.btnPayment.text = "Đã sử dụng"
                }
                else if(it.paymentStatus == 1){
                    binding.btnPayment.text = "Đánh dấu đã sử dụng"
                }
                fragmentViewTicketViewModel.getListTicket(it.paymentId)
            }
            else{
                Toast.makeText(requireContext(), "Payment not found", Toast.LENGTH_SHORT).show()
            }
        })
        fragmentViewTicketViewModel.listTicket.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            if (it.isEmpty() || it == null){
                adapter.updateList(mutableListOf())
            }
            else{
                listTicket.addAll(it)
                adapter.updateList(it)
            }
        })
    }

    override fun onItemClick(position: Int) {
        val ticket = listTicket[position].ticket
        detailTicketActivityViewModel.setTicket(ticket!!)
        val fragment = FragmentDetailTicket()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment, fragment)
        transaction.addToBackStack(null)
        transaction.hide(this)
        transaction.commit()
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
        TODO("Not yet implemented")
    }

}