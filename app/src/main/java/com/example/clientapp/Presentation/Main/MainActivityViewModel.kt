package com.example.clientapp.Presentation.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Response.TokenRespose
import com.example.clientapp.Domain.Repository.TokenRepository
import com.example.clientapp.Domain.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val tokenRepository: TokenRepository, private val userRepository: UserRepository):ViewModel() {
    private var _validateUser = MutableLiveData<TokenRespose>()
    val validateUser: LiveData<TokenRespose> get() = _validateUser

    fun validateUser(){
        viewModelScope.launch(Dispatchers.IO) {
            val token = "Bearer ${tokenRepository.getTokenValid()}"
            val response = userRepository.validateUser(token)
            delay(2000)
            if (response.status == 1) {
                tokenRepository.saveToken(tokenRepository.getTokenValid()!!, true)
            }
            _validateUser.postValue(response)
        }
    }
}