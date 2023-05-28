package com.bts.mypaint.ui

import androidcommon.utils.UiState
import androidx.lifecycle.*
import com.bts.mypaint.data.repository.AuthRepository
import com.bts.mypaint.data.repository.databaseReppo.DbRepository
import com.bts.mypaint.domain.dbmodel.TblCalculation
import com.bts.mypaint.domain.dbmodel.TblColorants
import com.bts.mypaint.domain.dbmodel.TblColors
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: AuthRepository,
    private val dbRepository: DbRepository
) : ViewModel() {


    private val _colorantsFromServer = MutableLiveData<UiState<List<TblColorants>>>()
    val colorantsFromServer: LiveData<UiState<List<TblColorants>>> = _colorantsFromServer
    fun getColorantsFromServer() {
        viewModelScope.launch {
            _colorantsFromServer.value = UiState.Loading()
            _colorantsFromServer.value = homeRepository.getColorantsFromServer()
        }
    }


    private val _colorsFromServer = MutableLiveData<UiState<List<TblColors>>>()
    val colorsFromServer: LiveData<UiState<List<TblColors>>> = _colorsFromServer
    fun getColorsFromServer() {
        viewModelScope.launch {
            _colorsFromServer.value = UiState.Loading()
            _colorsFromServer.value = homeRepository.getColorsFromServer()
        }
    }

    fun insert(colorant: TblColorants) {
        dbRepository.insert(colorant)
    }

    fun insert(color: TblColors) {
        dbRepository.insert(color)
    }

    fun deleteColors() {
        dbRepository.deleteColors()
    }

    fun deleteColorants() {
        dbRepository.deleteColorants()
    }

    var fandecksList: LiveData<List<TblColors>> = dbRepository.getAllFandecks().asLiveData()
    fun getFandecks() {
        viewModelScope.launch {
            fandecksList = dbRepository.getAllFandecks().asLiveData()
        }
    }


    var productsList: LiveData<List<TblColors>> = dbRepository.getAllProducts("apple").asLiveData()

    fun getProducts(cardName: String?) {
        viewModelScope.launch {
            productsList = dbRepository.getAllProducts(cardName).asLiveData()
        }
    }


    var colorsList: LiveData<List<TblColors>>? = null
    fun getColors(fandeck: String?, product: String?) {
        viewModelScope.launch {
            colorsList = dbRepository.getColors(fandeck, product).asLiveData()
        }
    }




    private val _calcuList = MutableLiveData<List<TblCalculation>>()
    val calcuList: LiveData<List<TblCalculation>> = _calcuList
    fun getColorantv2(selectedColor: TblColors, selectedQty: Float) {
        viewModelScope.launch {
            val colorants: MutableList<TblCalculation> = arrayListOf()
            if (!selectedColor.cnt1.isNullOrEmpty()) {
                val color = TblCalculation(
                    selectedColor.cnt1,
                    selectedColor.qnt1!!*selectedQty,
                    dbRepository.getColorants(selectedColor.cnt1)
                )
                colorants.add(color);
            }
            if (!selectedColor.cnt2.isNullOrEmpty()) {
                val color = TblCalculation(
                    selectedColor.cnt2,
                    selectedColor.qnt2!!*selectedQty,
                    dbRepository.getColorants(selectedColor.cnt2)
                )
                colorants.add(color);
            }
            if (!selectedColor.cnt3.isNullOrEmpty()) {
                val color = TblCalculation(
                    selectedColor.cnt3,
                    selectedColor.qnt3!!*selectedQty,
                    dbRepository.getColorants(selectedColor.cnt3)
                )
                colorants.add(color);

            }
            if (!selectedColor.cnt4.isNullOrEmpty()) {
                val color = TblCalculation(
                    selectedColor.cnt4,
                    selectedColor.qnt4!!*selectedQty,
                    dbRepository.getColorants(selectedColor.cnt4)
                )
                colorants.add(color);
            }
            if (!selectedColor.cnt5.isNullOrEmpty()) {
                val color = TblCalculation(
                    selectedColor.cnt5,
                    selectedColor.qnt5!!*selectedQty,
                    dbRepository.getColorants(selectedColor.cnt5)
                )
                colorants.add(color);
            }

            _calcuList.value = colorants
        }



    }


}