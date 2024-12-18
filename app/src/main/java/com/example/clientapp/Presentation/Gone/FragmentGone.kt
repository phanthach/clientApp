package com.example.clientapp.Presentation.Present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clientapp.databinding.FragmentGoneBinding
import com.example.clientapp.databinding.FragmentPresentBinding

class FragmentGone: Fragment() {
    private var _binding: FragmentGoneBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoneBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
}