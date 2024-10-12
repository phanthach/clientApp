package com.example.clientapp.Presentation.Register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.clientapp.Domain.Model.User
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.R
import com.example.clientapp.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        registerViewModel.registerResponse.observe(this, { registerResponse ->
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

    private fun getData() {
        binding.btRegister.setOnClickListener{
            var fullname = binding.edtFullName.text.toString()
            var birthDay = binding.edtDateOfBirth.text.toString()
            var email = binding.editEmail.text.toString()
            var address = binding.edtAddress.text.toString()
            var password = binding.edtPassword.text.toString()
            var confirmPassword = binding.edtConfirmPassword.text.toString()
            var phone = binding.edtPhoneNumber.text.toString()
            if(password!=confirmPassword){
                Toast.makeText(this, "Nhập lại mật khẩu", Toast.LENGTH_LONG).show()
            }
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val formattedDate = currentDate.format(formatter)

            println("Ngày hiện tại (định dạng): $formattedDate")
            val user = User(fullname = fullname, birthDay = birthDay,
                email = null, address = address,
                password = password, phoneNumber = phone,
                roleId = 4, createdAt = formattedDate, isBlocked = 0, licenseNumber = null, companyName = null)
            Log.e("UserRegister", "$user")
            registerViewModel.registerUser(user)
        }
    }
}