package com.example.clientapp.Presentation.UI.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Data.Network.ApiUserService
import com.example.clientapp.Domain.Model.Request.LoginRequest
import com.example.clientapp.Domain.Model.Response.LoginResponse
import com.example.clientapp.Domain.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>();
    val loginResult: LiveData<LoginResponse> = _loginResult;

    private val _islogin =MutableLiveData<Boolean>()
    val islogin:LiveData<Boolean> = _islogin

    fun checklogin(phoneNumber: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val loginRequest = LoginRequest(phoneNumber, password)
                val response = userRepository.loginUser(loginRequest)
                withContext(Dispatchers.Main){
                    _loginResult.value = response
                }
            }catch (e: Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    _loginResult.value = LoginResponse("An error occurred", 0)
                }
            }
        }

    }
}