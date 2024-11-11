package com.example.clientapp.Presentation.Register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.clientapp.Domain.Model.Model.User
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.databinding.ActivityRegisterUserBinding
import dagger.hilt.android.AndroidEntryPoint
import showDateTimeDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private val registerViewModel: RegisterUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        setDateDialog()
    }

    private fun setDateDialog() {
        binding.edtDateOfBirth.setOnClickListener{
            showDateTimeDialog(this){ selectedDate ->
                binding.edtDateOfBirth.setText(selectedDate)
            }
        }
    }

    private fun getData() {
        binding.btRegister.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            binding.btRegister.isEnabled = false
            var fullname = binding.edtFullName.text.toString()
            var birthDay = binding.edtDateOfBirth.text.toString()
            var email = binding.editEmail.text.toString()
            var address = binding.edtAddress.text.toString()
            var password = binding.edtPassword.text.toString()
            var confirmPassword = binding.edtConfirmPassword.text.toString()
            var phone = binding.edtPhoneNumber.text.toString()
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formattedDate = currentDate.format(formatter)
            if(password!=confirmPassword){
                Toast.makeText(this, "Nhập lại mật khẩu", Toast.LENGTH_LONG).show()
            }
            else{
                println("Ngày hiện tại (định dạng): $formattedDate")
                val user = User(fullname = fullname, birthDay = birthDay,
                    email = email, address = address,
                    password = password, phoneNumber = phone,
                    roleId = 4, createdAt = formattedDate, isBlocked = 0, licenseNumber = null, companyName = null)
                Log.e("UserRegister", "$user")
                registerViewModel.registerUser(user)
            }

        }
        registerViewModel.registerResponse.observe(this, { registerResponse ->
            binding.progressBar.visibility = View.GONE
            binding.btRegister.isEnabled = true
            if(registerResponse.status==1){
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_LONG).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_LONG).show()
                Log.e("UserRegister", "${registerResponse.message}")
            }
        })
    }
}