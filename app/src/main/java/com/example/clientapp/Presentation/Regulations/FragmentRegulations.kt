package com.example.clientapp.Presentation.Regulations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.example.clientapp.Presentation.SelectVehicle.FragmentSelectVehicle
import com.example.clientapp.R
import com.example.clientapp.databinding.FragmentRegulationsBinding

class FragmentRegulations: Fragment() {
    private var _binding: FragmentRegulationsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegulationsBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continueOnClick()
        binding.btBack.setOnClickListener { requireActivity().onBackPressed() }
    }
    private fun continueOnClick() {
        val fragment = FragmentSelectVehicle()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        binding.btContinue.setOnClickListener{
            if(binding.rbAgree.isChecked){
                transaction.replace(R.id.fragment, fragment)
                transaction.commit()
            }else{
                Toast.makeText(requireContext(), "Vui lòng đồng ý với các điều khoản trên để tiếp tục", Toast.LENGTH_SHORT).show()
            }
        }
    }
}