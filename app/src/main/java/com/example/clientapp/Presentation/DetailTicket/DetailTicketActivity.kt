package com.example.clientapp.Presentation.DetailTicket

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import com.example.clientapp.Domain.Model.Model.Payment
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityBookTicketBinding
import com.example.clientapp.databinding.ActivityDetailTicketBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTicketBinding
    private val detailTicketActivityViewModel: DetailTicketActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.fragment.id, FragmentViewTicket() ).commit()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}