package com.example.clientapp.Presentation.Main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.clientapp.Presentation.DriverActivity.DriverActivity
import com.example.clientapp.Presentation.Login.LoginActivity
import com.example.clientapp.Presentation.UserActivity.UserActivity
import com.example.clientapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainActivityViewModel.validateUser()
        mainActivityViewModel.validateUser.observe(this, {
            if (it.status == 1) {
                if(it.roleId==4){
                    mainActivityViewModel.checkToken()
                    val intent = Intent(this, UserActivity::class.java)
                    intent.putExtra("user", it.fullName)
                    startActivity(intent)
                    finish()
                }
                else if(it.roleId==3){
                    mainActivityViewModel.checkToken()
                    val intent = Intent(this, DriverActivity::class.java)
                    intent.putExtra("user", it.fullName)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("user", it.fullName)
                startActivity(intent)
                finish()
            }
        })
    }
}