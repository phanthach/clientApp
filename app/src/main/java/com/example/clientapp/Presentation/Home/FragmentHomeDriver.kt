package com.example.clientapp.Presentation.Home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.clientapp.Presentation.BookTicket.BookTicketActivity
import com.example.clientapp.Presentation.SelectLocation.SelectLocationActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentHomeBinding
import com.example.clientapp.databinding.FragmentHomeDriverBinding
import dagger.hilt.android.AndroidEntryPoint
import showDateTimeDialog
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class FragmentHomeDriver : Fragment() {
    private var _binding: FragmentHomeDriverBinding? = null
    private val fragmentHomeViewModel: FragmentHomeViewModel by viewModels()
    private val binding get() = _binding!!
    private var departureDate: Long? = null
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
    }

    override fun onResume() {
        super.onResume()
    }
}