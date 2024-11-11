package com.example.clientapp.Presentation.VehicleDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.LayoutSeat
import com.example.clientapp.Domain.UseCase.LayoutUseCase.GetLayoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentSelectVehicleViewModel @Inject constructor(private val getLayoutUseCase: GetLayoutUseCase):ViewModel()  {
    private var _layout = MutableLiveData<LayoutSeat?>()
    val layout: LiveData<LayoutSeat?> get() = _layout

    fun getLayout(layoutId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getLayoutUseCase.getLayout(layoutId)
                withContext(Dispatchers.Main) {
                    _layout.postValue(response)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    _layout.postValue(null)
                }
            }
        }
    }
}