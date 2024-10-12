package com.example.clientapp.Presentation.BookTicket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientapp.Presentation.Regulations.FragmentRegulations
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityBookTicketBinding

class BookTicketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBookTicketBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(binding.fragment.id, FragmentRegulations() ).commit()
    }
}