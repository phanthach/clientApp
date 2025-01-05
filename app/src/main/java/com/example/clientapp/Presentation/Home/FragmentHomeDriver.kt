package com.example.clientapp.Presentation.Home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
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
import com.example.clientapp.Domain.Model.Model.Trip
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Model.Response.TripResponse
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.BookTicket.BookTicketActivity
import com.example.clientapp.Presentation.SelectLocation.SelectLocationActivity
import com.example.clientapp.Presentation.TripDetailDriver.TripDetailActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.CustomAlertDialogBinding
import com.example.clientapp.databinding.FragmentHomeBinding
import com.example.clientapp.databinding.FragmentHomeDriverBinding
import com.mapbox.maps.plugin.logo.generated.LogoSettings
import dagger.hilt.android.AndroidEntryPoint
import showDateTimeDialog
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class FragmentHomeDriver : Fragment(), ItemListener {
    private var _binding: FragmentHomeDriverBinding? = null
    private val homeDriverViewModel: HomeDriverViewModel by viewModels()
    private val binding get() = _binding!!
    private var departureDate: Long? = null
    private lateinit var adapter: ListTripAdapter
    private var tripVehecle:MutableList<TripVehicle> = mutableListOf()
    private var page = 0
    private var totalPage =0
    private var tripSelect: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeDriverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListTripAdapter(mutableListOf(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        fillter()
        getListTrips(page, getCurrentDate())
    }

    private fun fillter() {
        binding.chonNgayDi.setOnClickListener{
            showDateTimeDialog(requireContext()){selectDate ->
                binding.chonNgayDi.setText(selectDate)
                departureDate = convertDateToMillis(selectDate)
            }
        }
        homeDriverViewModel.setDepartureDate(departureDate.toString())
    }
    private fun convertDateToMillis(date: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(date)?.time ?: System.currentTimeMillis()
    }
    private fun getListTrips(page:Int, departureDate: String?) {
        homeDriverViewModel.getTripsDriver(page, departureDate!!)
        homeDriverViewModel.trips.observe(viewLifecycleOwner, Observer {
            val listTrips: TripResponse = it
            tripVehecle.clear()
            tripVehecle.addAll(listTrips.content)
            adapter.updateList(tripVehecle)
            totalPage = listTrips.totalPages
            binding.progressBar.visibility = View.GONE
        })
    }
    fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
    override fun onItemClick(position: Int) {
        if(tripVehecle[position].trip.status == 2){
            val binding = CustomAlertDialogBinding.inflate(layoutInflater)
            binding.tvTitle.text = "Bắt đầu"
            binding.tvMessage.text = "Bạn có muốn bắt đầu chuyến xe này không?"

            val dialog = AlertDialog.Builder(requireContext())
                .setView(binding.root)  // Set the custom layout
                .setCancelable(false)   // Prevent dismiss on outside touch
                .create()

            binding.btnCancel.setOnClickListener {
                dialog.dismiss() // Close the dialog
            }
            binding.btnConfirm.setOnClickListener {
                homeDriverViewModel.updateTrip(tripVehecle[position].trip.tripId, 3)
                val intent = Intent(requireContext(),TripDetailActivity::class.java)
                intent.putExtra("trip",tripVehecle[position].trip)
                intent.putExtra("userId",tripVehecle[position].trip.driverId)
                startActivity(intent)
                dialog.dismiss() // Close the dialog
            }
            dialog.show()
        }
        else{
            val intent = Intent(requireContext(),TripDetailActivity::class.java)
            intent.putExtra("trip",tripVehecle[position].trip)
            intent.putExtra("userId",tripVehecle[position].trip.driverId)
            startActivity(intent)
        }
    }

    override fun onItemClickSelected(position: Int, isSelected: Boolean) {
    }
}