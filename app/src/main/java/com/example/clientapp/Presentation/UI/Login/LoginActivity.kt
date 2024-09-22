package com.example.clientapp.Presentation.UI.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.clientapp.Presentation.UI.Main.MainActivity
import com.example.clientapp.Presentation.UI.Register.RegisterActivity
import com.example.clientapp.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
            binding.btLogin.isEnabled = false
            val username = binding.userName.text.toString()
            val password = binding.passWord.text.toString()
            if(username.isEmpty()|| password.isEmpty()){
                binding.errLogin.setTextColor(Color.RED)
                binding.errLogin.visibility = View.VISIBLE
                binding.errLogin.text = "Tài khoản và mật khẩu không được để trống"
            }
            else{
                binding.progressBar.visibility = View.VISIBLE
                loginViewModel.checklogin(username, password)
            }
        }
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginViewModel.loginResult.observe(this, Observer{ loginResult->
            Log.e("LoginActivity", loginResult.toString())
            binding.progressBar.visibility = View.GONE
            when(loginResult.status){
                0->{
                    binding.errLogin.visibility = View.VISIBLE
                    binding.btLogin.isEnabled = true
                    binding.errLogin.setTextColor(Color.RED)
                    binding.errLogin.text = loginResult.message
                }
                1->{
                    binding.errLogin.visibility = View.VISIBLE
                    binding.errLogin.setTextColor(Color.GREEN)
                    binding.errLogin.text = loginResult.message
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    println()
                    finish()
                }
                2->{
                    binding.errLogin.visibility = View.VISIBLE
                    binding.btLogin.isEnabled = true
                    binding.errLogin.setTextColor(Color.RED)
                    binding.errLogin.text = loginResult.message
                }
            }
        })
    }
}