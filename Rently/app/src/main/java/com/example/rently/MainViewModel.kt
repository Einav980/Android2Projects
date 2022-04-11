package com.example.rently

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.model.Apartment
import com.example.rently.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<ArrayList<Apartment>> = MutableLiveData()
    val currentApartment: MutableLiveData<Apartment> = MutableLiveData()

    fun listApartments(){
        viewModelScope.launch {
            val response = repository.listApartments()
            myResponse.value = response
        }
    }

    fun getApartment(){
        viewModelScope.launch {
            val response = repository.getApartment()
            currentApartment.value = response
        }
    }

}