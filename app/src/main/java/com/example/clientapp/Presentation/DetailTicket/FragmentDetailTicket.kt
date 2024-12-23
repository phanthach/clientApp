package com.example.clientapp.Presentation.DetailTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentDetailTicketBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class FragmentDetailTicket: Fragment() {
    private var _binding: FragmentDetailTicketBinding? = null
    private val binding get() = _binding!!
    private val fragmentDetailTicketViewModel: FragmentDetailTicketViewModel by viewModels()
    private val detailTicketActivityViewModel: DetailTicketActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTicketBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        getTicketDetail()
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun getTicketDetail() {
        val ticket: Ticket = detailTicketActivityViewModel.ticket.value!!
        if(ticket.status == 2){
            binding.btnPrint.isEnabled = true
            binding.btnPrint.setBackgroundResource(R.drawable.button_green_border)
        }
        else{
            binding.btnPrint.isEnabled = false
            binding.btnPrint.setBackgroundResource(R.drawable.boder_diss)
        }
        fragmentDetailTicketViewModel.getTicketDetail(ticket.ticketId)
        fragmentDetailTicketViewModel.ticketResponse.observe(viewLifecycleOwner, {ticketResponse->
            if(ticketResponse== null || ticketResponse.ticket == null){
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.tvError.visibility = View.GONE
                if(ticketResponse.ticket!!.status == 2){
                    Glide.with(binding.root)
                        .load(ticketResponse.ticket!!.qrCode)
                        .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)
                        .into(binding.qrCode)
                }
                binding.tvTicketCode.text = ticketResponse.ticket!!.ticketCode
                binding.tvNameVehicle.text = ticketResponse.trip!!.nameVehicle
                binding.tvPlateNumber.text = ticketResponse.vehicle!!.plateNumber
                binding.tvPickUp.text = ticketResponse.pickupLocation!!.nameLocation
                binding.tvTimePickUp.text = ticketResponse.pickupLocation!!.arrivalTime
                binding.tvNameSeat.text = ticketResponse.seat!!.nameSeat
                binding.tvPrice.text = ticketResponse.ticket!!.ticketPrice.toString()
                val date = convertDateFormat(ticketResponse.ticket!!.bookingTime)
                binding.tvXN.text = date
                binding.ttScrollView.visibility = View.VISIBLE
            }
        })
    }
    fun convertDateFormat(inputDate: String): String {
        // Định dạng gốc của chuỗi ngày tháng
        val inputFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
        // Định dạng mong muốn
        val outputFormat = SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy", Locale.getDefault())

        return try {
            // Chuyển chuỗi thành đối tượng Date
            val date: Date = inputFormat.parse(inputDate) ?: return "Ngày không hợp lệ"
            // Định dạng lại ngày tháng
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Ngày không hợp lệ"
        }
    }
}