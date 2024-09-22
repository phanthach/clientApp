package com.example.clientapp.Presentation.UI.Register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Response.RegisterResponse
import com.example.clientapp.Domain.Model.User
import com.example.clientapp.Domain.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    fun registerUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.registerUser(user)
                withContext(Dispatchers.Main){
                    _registerResponse.value = response
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}