package com.example.clientapp.Presentation.UI.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Data.Network.ApiUserService
import com.example.clientapp.Domain.Model.Request.LoginRequest
import com.example.clientapp.Domain.Model.Response.LoginResponse
import com.example.clientapp.Domain.Repository.TokenRepository
import com.example.clientapp.Domain.Repository.UserRepository
import com.example.clientapp.Domain.UseCase.LoginUseCase.LoginUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
): ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>();
    val loginResult: LiveData<LoginResponse> = _loginResult;

    private val _islogin =MutableLiveData<Boolean>()
    val islogin:LiveData<Boolean> = _islogin

    fun checklogin(phoneNumber: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = loginUseCase.checkLogin(phoneNumber, password)
                withContext(Dispatchers.Main){
                    _loginResult.value = response
                }
            }catch (e: Exception){
                e.printStackTrace()
                withContext(Dispatchers.Main){
                    _loginResult.value = LoginResponse("An error", 0,null,null, null, null)
                }
            }
        }
    }
    fun deleteToken(){
        loginUseCase.deleteToken()
    }
    fun saveToken(token: String, isRemember: Boolean){
        loginUseCase.saveToken(token, isRemember)
    }
    fun checkToken(){
        val tokenFCM = tokenRepository.getFCMToken()
        Log.d("FCM Token", tokenFCM!!)
        if(tokenFCM == null){
            FirebaseMessaging.getInstance().token
                .addOnCompleteListener { task: Task<String?> ->
                    if (!task.isSuccessful) {
                        return@addOnCompleteListener
                    }
                    val token = task.result
                    Log.d("FCM Token", token!!)
                    tokenRepository.saveFCMToken(token!!)
                    viewModelScope.launch(Dispatchers.IO) {
                        userRepository.saveFCMToken("Bearer ${tokenRepository.getTokenValid()!!}", token)
                    }
                }
        }
    }
}