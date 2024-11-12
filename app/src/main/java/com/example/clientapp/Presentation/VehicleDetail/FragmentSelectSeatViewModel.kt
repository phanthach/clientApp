package com.example.clientapp.Presentation.VehicleDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientapp.Domain.Model.Model.LayoutSeat
import com.example.clientapp.Domain.UseCase.LayoutUseCase.GetLayoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FragmentSelectSeatViewModel @Inject constructor(private val getLayoutUseCase: GetLayoutUseCase):ViewModel()  {
    private var _layout = MutableLiveData<LayoutSeat?>()
    val layout: LiveData<LayoutSeat?> get() = _layout

    private var _listNameSeat = MutableLiveData<List<String>>()
    val listNameSeat: LiveData<List<String>> get() = _listNameSeat

    private var _listSeatID = MutableLiveData<List<Int>>()
    val listSeatID: LiveData<List<Int>> get() = _listSeatID

    private var _priceSeat = MutableLiveData<Int>()
    val priceSeat: LiveData<Int> get() = _priceSeat

    private var listNameSeatBackUp : MutableList<String> = mutableListOf()
    private var listSeatIdBackUp : MutableList<Int> = mutableListOf()
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
    fun updateListNameSeat(seatID: Int,listNameSeat: String){
        listNameSeatBackUp.add(listNameSeat)
        listSeatIdBackUp.add(seatID)
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun removeListNameSeat(seatID: Int, nameSeat: String){
        listNameSeatBackUp.remove(nameSeat)
        listSeatIdBackUp.remove(seatID)
        _listNameSeat.postValue(listNameSeatBackUp)
        _listSeatID.postValue(listSeatIdBackUp)
    }
    fun updatePriceSeat(price: Int){
        _priceSeat.postValue(price)
    }
}