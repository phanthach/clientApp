package com.example.clientapp.Presentation.Present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clientapp.databinding.FragmentPresentBinding

class FragmentPresent: Fragment() {
    private var _binding: FragmentPresentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPresentBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
}