package com.example.clientapp.Presentation.VehicleDetail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientapp.Domain.Repository.ItemSelectSeat
import com.example.clientapp.Presentation.BookTicket.BookTicketActivityViewModel
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.databinding.FragmentSelectSeatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSelectSeat: Fragment(), ItemSelectSeat {
    private var _binding: FragmentSelectSeatBinding? = null
    private val bookTicketActivityViewModel: BookTicketActivityViewModel by activityViewModels()
    private val fragmentSelectSeatViewModel: FragmentSelectSeatViewModel by activityViewModels()
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
        adapter = SelectSeatRecycleViewAdapter(null, this)
        binding.rvSeat.adapter = adapter
        binding.rvSeat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        setUpInfo()
        getListSeat()
    }

    private fun getListSeat() {
        bookTicketActivityViewModel.trips.observe(viewLifecycleOwner, {
            fragmentSelectSeatViewModel.getLayout(it.vehicle.layoutId)
            fragmentSelectSeatViewModel.updatePriceSeat(it.trip.ticketPrice)
        })
        fragmentSelectSeatViewModel.layout.observe(viewLifecycleOwner, {layout ->
            layout?.let {
                if (layout.status==200){
                    adapter.updateData(layout)
                }
                else{
                    AlertDialog.Builder(requireContext())
                        .setTitle("Lỗi")
                        .setMessage("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss() // Đóng dialog
                            val inten = Intent(requireContext(), LoginActivity::class.java)
                            startActivity(inten)
                        }.show()
                }
            }
        })
    }

    private fun setUpInfo() {
        binding.btBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.btnContinue.setOnClickListener{

        }
    }

    override fun onItemSelect(isSelect: Boolean, nameSeat: String, seatId: Int, position: Int) {
        if (isSelect){
            fragmentSelectSeatViewModel.updateListNameSeat(seatId, nameSeat)
        }
        else{
            fragmentSelectSeatViewModel.removeListNameSeat(seatId, nameSeat)
        }
        fragmentSelectSeatViewModel.listNameSeat.observe(viewLifecycleOwner, {listNameSeat ->
            binding.tvTotalSeat.text = listNameSeat.size.toString()
            binding.tvNameSeat.text = listNameSeat.joinToString(",")
            fragmentSelectSeatViewModel.priceSeat.observe(viewLifecycleOwner, {priceSeat->
                binding.tvTotalPrice.text = (priceSeat*listNameSeat.size).toString()+" VNĐ"
            })
        })
    }

}