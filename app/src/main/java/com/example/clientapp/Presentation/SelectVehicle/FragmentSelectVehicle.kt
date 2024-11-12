package com.example.clientapp.Presentation.SelectVehicle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clientapp.Domain.Model.Response.TripResponse
import com.example.clientapp.Domain.Model.Model.TripVehicle
import com.example.clientapp.Domain.Repository.ItemListener
import com.example.clientapp.Presentation.BookTicket.BookTicketActivityViewModel
import com.example.clientapp.Presentation.Home.FragmentHomeViewModel
import com.example.clientapp.Presentation.VehicleDetail.FragmentSelectSeat
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentSelectVehicleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSelectVehicle: Fragment(), ItemListener {
    private var _binding: FragmentSelectVehicleBinding? = null
    private val binding get() = _binding!!
    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()
    private val fragmentSelectVehicleViewModel: FragmentSelectVehicleViewModel by viewModels()
    private val bookTicketActivityViewModel: BookTicketActivityViewModel by activityViewModels()
    private var page = 0
    private var totalPage =0
    private var tripSelect: Int? = null
    private var tripVehecle:MutableList<TripVehicle> = mutableListOf()
    private lateinit var adapter: ListVehicleAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectVehicleBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListVehicleAdapter(mutableListOf(), this)
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
        setContinueButton(roundTrip)
        getListTrips(page,pickUpPoint,dropOffPoint,departureDate)
        Log.d("FragmentSelectVehicle", "onViewCreated: ")
        checkListTrips(pickUpPoint,dropOffPoint,departureDate)
    }
    private fun checkListTrips(pickUpPoint: String?, dropOffPoint: String?, departureDate: String?) {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy>0){
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount){
                        if(page<totalPage-1){
                            page++
                            binding.progressBar.visibility = View.VISIBLE
                            fragmentSelectVehicleViewModel.getTrips(page,pickUpPoint!!, dropOffPoint!!, departureDate!!)
                        }
                    }
                }
            }
        })
    }

    private fun getListTrips(page:Int, pickUpPoint: String?, dropOffPoint: String?, departureDate: String?) {
        fragmentSelectVehicleViewModel.getTrips(page,pickUpPoint!!, dropOffPoint!!, departureDate!!)
        fragmentSelectVehicleViewModel.trips.observe(viewLifecycleOwner, Observer {
            val listTrips: TripResponse = it
            tripVehecle.addAll(listTrips.content)
            adapter.updateList(tripVehecle)
            totalPage = listTrips.totalPages
            binding.progressBar.visibility = View.GONE
        })
    }

    private fun setContinueButton(roundTrip: Boolean) {
        binding.btContinue.setOnClickListener {
            if(roundTrip==true){
                //Add fragment FragmentSelectVehicleReturnTrip
                val fragment = FragmentSelectVehicleReturnTrip()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                bookTicketActivityViewModel.getTrips(tripVehecle.get(tripSelect!!))
                transaction.add(R.id.fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else {
                //Add fragment FragmentSelectVehicleOneWay
                val fragment = FragmentSelectSeat()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.add(R.id.fragment, fragment)
                bookTicketActivityViewModel.getTrips(tripVehecle.get(tripSelect!!))
                transaction.addToBackStack(null)
                transaction.hide(this)
                transaction.commit()
            }
        }
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
            binding.bottomBar.visibility = View.VISIBLE
            tripSelect = position
        }
        else{
            binding.bottomBar.visibility = View.GONE
            tripSelect=null
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("FragmentSelectVehicle", "onPause: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentSelectVehicle", "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("FragmentSelectVehicle", "onDestroy: ")
    }
}