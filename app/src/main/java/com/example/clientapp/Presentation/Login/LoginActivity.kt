package com.example.clientapp.Presentation.Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.clientapp.Presentation.DriverActivity.DriverActivity
import com.example.clientapp.Presentation.UserActivity.UserActivity
import com.example.clientapp.Presentation.Register.RegisterUserActivity
import com.example.clientapp.Presentation.UI.Login.LoginViewModel
import com.example.clientapp.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private var isRemember = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.remember.setOnClickListener{
            if(binding.remember.isChecked){
                isRemember = true
            }else{
                isRemember = false
            }
        }

        binding.btLogin.setOnClickListener {
            binding.btLogin.isEnabled = false
            val username = binding.userName.text.toString()
            val password = binding.passWord.text.toString()
            if(username.isEmpty()|| password.isEmpty()){
                binding.errLogin.setTextColor(Color.RED)
                binding.errLogin.visibility = View.VISIBLE
                binding.errLogin.text = "Tài khoản và mật khẩu không được để trống"
                binding.btLogin.isEnabled = true
            }
            else{
                binding.progressBar.visibility = View.VISIBLE
                loginViewModel.checklogin(username, password)
            }
        }
        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
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
                    loginResult.token?.let {
                        loginViewModel.saveToken(it, isRemember)}
                    if(loginResult.roleId==4){
                        loginViewModel.checkToken()
                        val intent = Intent(this, UserActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else if (loginResult.roleId==3){
                        loginViewModel.checkToken()
                        val intent = Intent(this, DriverActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        binding.errLogin.visibility = View.VISIBLE
                        binding.errLogin.setTextColor(Color.RED)
                        binding.btLogin.isEnabled = true
                        binding.errLogin.text = "Tài khoản không tồn tại"
                        loginViewModel.deleteToken()
                    }
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