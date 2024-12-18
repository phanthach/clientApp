package com.example.clientapp.Presentation.VehicleDetail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.clientapp.Domain.Model.Model.Ticket
import com.example.clientapp.Presentation.BookTicket.BookTicketActivityViewModel
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.Presentation.Main.MainActivityViewModel
import com.example.clientapp.Presentation.Pay.PayActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentPayBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPay: Fragment() {
    private var _binding: FragmentPayBinding? = null
    private  val binding get() = _binding!!
    private val bookTicketActivityViewModel: BookTicketActivityViewModel by activityViewModels()
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val fragmentPayViewModel: FragmentPayViewModel by viewModels()
    private var roundTrip:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roundTrip = bookTicketActivityViewModel.roundTrip.value?: false
        if(!roundTrip){
            binding.roundTrip.visibility = View.GONE
        }
        mainActivityViewModel.validateUser()
        mainActivityViewModel.validateUser.observe(viewLifecycleOwner,{token ->
            if(token==null){
                binding.view.visibility = View.GONE
            }
            else if(token.status == 0){
                AlertDialog.Builder(requireContext())
                    .setTitle("Lỗi")
                    .setMessage("Đã có lỗi xảy ra, vui lòng đăng nhập lại")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss() // Đóng dialog
                        val inten = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(inten)
                    }.show()
            }
            else{
                binding.Name.text = token.fullName
                binding.phoneNumber.text = token.message
                binding.view.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
        binding.btBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        setUpTTCD()
        setUpButtonPayment()
    }

    private fun setUpButtonPayment() {
        fragmentPayViewModel.paymentResult.observe(viewLifecycleOwner,{result ->
            if(result !=-1){
                val intent = Intent(requireContext(), PayActivity::class.java)
                intent.putExtra("paymentId",result)
                startActivity(intent)
            }
            else{
                binding.btnPayment.visibility = View.VISIBLE
                binding.progressBarBtn.visibility = View.GONE
                AlertDialog.Builder(requireContext())
                    .setTitle("Lỗi")
                    .setMessage("Đã có lỗi xảy ra, vui lòng thử lại")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss() // Đóng dialog
                    }.show()
            }
        })
    }

    private fun setUpTTCD() {
        if(!roundTrip){
            val item = bookTicketActivityViewModel.trips.value!!
            Glide.with(binding.root)
                .load(item.vehicle.img)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(binding.imageVehicle1)

            binding.tvNameVehicle1.text = item.trip.nameVehicle
            binding.diemDon1.text = bookTicketActivityViewModel.locationPickUpNameTrip.value
            binding.diemTra1.text = bookTicketActivityViewModel.locationDropOffNameTrip.value
            val price1 = (bookTicketActivityViewModel.listNameSeat.value!!.size * item.trip.ticketPrice)
            binding.Price.text = price1.toString()
            binding.total.text = (bookTicketActivityViewModel.listNameSeat.value!!.size).toString()
            val oderInfo = "Thanh toan ve xe"
            val listTickets = mutableListOf<Ticket>()
            for (ticket in bookTicketActivityViewModel.listSeatID.value!!){
                listTickets.add(Ticket(item.trip.tripId, ticket, bookTicketActivityViewModel.locationPickUpTrip.value!!, bookTicketActivityViewModel.locationDropOffTrip.value!!))
            }
            binding.btnPayment.setOnClickListener {
                binding.btnPayment.visibility = View.GONE
                binding.progressBarBtn.visibility = View.VISIBLE
                fragmentPayViewModel.submitOrder(price1,oderInfo,listTickets,null, item.trip.modId, -1, 1, item.trip.ticketPrice, -1)
            }
        }
        else{
            val item = bookTicketActivityViewModel.trips.value!!
            Glide.with(binding.root)
                .load(item.vehicle.img)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(binding.imageVehicle1)

            binding.tvNameVehicle1.text = item.trip.nameVehicle
            binding.diemDon1.text = bookTicketActivityViewModel.locationPickUpNameTrip.value
            binding.diemTra1.text = bookTicketActivityViewModel.locationDropOffNameTrip.value
            val item2 = bookTicketActivityViewModel.tripsReturn.value!!
            Glide.with(binding.root)
                .load(item2.vehicle.img)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(binding.imageVehicle1)

            binding.tvNameVehicle2.text = item2.trip.nameVehicle
            binding.diemDon2.text = bookTicketActivityViewModel.locationPickUpNameTripReturn.value
            binding.diemTra2.text = bookTicketActivityViewModel.locationDropOffNameTripReturn.value
            val price2 = (bookTicketActivityViewModel.listNameSeat.value!!.size * item.trip.ticketPrice) + (bookTicketActivityViewModel.listNameSeatReturn.value!!.size * item2.trip.ticketPrice)
            binding.Price.text = price2.toString()
            binding.total.text = (bookTicketActivityViewModel.listNameSeat.value!!.size +bookTicketActivityViewModel.listNameSeatReturn.value!!.size).toString()
            val oderInfo = "Thanh toan ve xe"
            val listTickets = mutableListOf<Ticket>()
            val listTicketsReturn = mutableListOf<Ticket>()
            for (ticket1 in bookTicketActivityViewModel.listSeatID.value!!){
                listTickets.add(Ticket(item.trip.tripId, ticket1, bookTicketActivityViewModel.locationPickUpTrip.value!!, bookTicketActivityViewModel.locationDropOffTrip.value!!))
            }
            for (ticket2 in bookTicketActivityViewModel.listSeatIDReturn.value!!){
                listTicketsReturn.add(Ticket(item2.trip.tripId, ticket2, bookTicketActivityViewModel.locationPickUpTripReturn.value!!, bookTicketActivityViewModel.locationDropOffTripReturn.value!!))
            }
            binding.btnPayment.setOnClickListener {
                binding.btnPayment.visibility = View.GONE
                binding.progressBarBtn.visibility = View.VISIBLE
                fragmentPayViewModel.submitOrder(price2,oderInfo, listTickets,listTicketsReturn, item.trip.modId, item2.trip.modId, 2, item.trip.ticketPrice, item2.trip.ticketPrice)
            }
        }
    }
}