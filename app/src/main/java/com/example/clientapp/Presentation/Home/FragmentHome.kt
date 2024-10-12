package com.example.clientapp.Presentation.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clientapp.Presentation.BookTicket.BookTicketActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSearchOnClick()
        btnRoundTripOnClick()
    }

    private fun btnRoundTripOnClick() {
        binding.roundTrip.setOnCheckedChangeListener{ buttonView,isChecked->
            if(isChecked){
                binding.tvTitle5.visibility = View.VISIBLE
                binding.tvTitle6.visibility = View.VISIBLE
                binding.chonNgayVe.visibility = View.VISIBLE
            }
            else{
                binding.tvTitle5.visibility = View.GONE
                binding.tvTitle6.visibility = View.GONE
                binding.chonNgayVe.visibility = View.GONE
            }
        }
    }

    private fun btnSearchOnClick() {
        binding.btnSearch.setOnClickListener{
            val intent = Intent(context, BookTicketActivity::class.java)
            startActivity(intent)
        }
    }
}