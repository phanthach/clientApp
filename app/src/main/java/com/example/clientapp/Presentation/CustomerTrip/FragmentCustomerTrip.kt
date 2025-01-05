package com.example.clientapp.Presentation.CustomerTrip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Response.TicketAllResponse
import com.example.clientapp.Domain.Model.Response.TicketResponse
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.MapTrip.SelectLocationAdapter
import com.example.clientapp.databinding.FragmentCustomerTripBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentCustomerTrip:Fragment(), ItemListener {
    private var _binding: FragmentCustomerTripBinding? = null
    val binding get() = _binding!!
    private lateinit var adapter: SelectCustomerAdapter
    private var tripId: Int? = null
    private val listCus: MutableList<TicketAllResponse> = mutableListOf()
    private val customerTripViewModel: CustomerTripViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerTripBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SelectCustomerAdapter(mutableListOf(), this)
        binding.rvCus.adapter = adapter
        binding.rvCus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val trip = activity?.intent?.getSerializableExtra("trip") as Trip
        if(trip!=null){
            tripId = trip.tripId
        }
        getInfoQR()
    }

    private fun getInfoQR() {
        binding.btnQRScan.setOnClickListener {
            binding.btnQRScan.setOnClickListener {
                val intentIntegrator = IntentIntegrator.forSupportFragment(this)
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                intentIntegrator.setPrompt("Đưa mã QR vào khung để quét")
                intentIntegrator.setCameraId(0) // Sử dụng camera phía sau
                intentIntegrator.setBeepEnabled(true) // Bật âm thanh khi quét thành công
                intentIntegrator.setBarcodeImageEnabled(false)
                intentIntegrator.initiateScan()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                val result = result.contents
                val list = result.split(",")
                val ticketCode = list[0]
                if(listCus!=null && listCus.isNotEmpty()){
                    for (i in listCus){
                        if(i.ticket!!.ticketCode.equals(ticketCode)){
                            customerTripViewModel.updateTicket(ticketCode)
                            customerTripViewModel.getAllTicketByTripId(tripId!!)
                        }
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    private fun getListCus() {
        customerTripViewModel.listTicketResponse.observe(viewLifecycleOwner, {
            if(it.isNotEmpty() && it!=null){
                listCus.clear()
                listCus.addAll(it)
                adapter.updateData(listCus)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        customerTripViewModel.getAllTicketByTripId(tripId!!)
        getListCus()
    }
    override fun onItemClick(position: Int) {
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
    }
}