package com.example.clientapp.Presentation.Present

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clientapp.databinding.FragmentCanceledBinding
import com.example.clientapp.databinding.FragmentGoneBinding

class FragmentCanceled: Fragment() {
    private var _binding: FragmentCanceledBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCanceledBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
}