package com.example.rently.ui.screens.apartments

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.User
import com.example.rently.repository.ApartmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ApartmentsViewModel @Inject constructor(private val repository: ApartmentRepository): ViewModel(){
    val apartments = mutableStateOf(ArrayList<Apartment>())
    val isLoading = mutableStateOf(false)

    fun listApartments() {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.listApartments()
            when(response){
                is Resource.Success -> {
                    apartments.value = response.data!!
                }
            }

            isLoading.value = false
        }
    }


    fun addApartment(apartment: Apartment) {
        viewModelScope.launch {
            val response = repository.addApartment(apartment = apartment)
            when(response){
                is Resource.Success -> {
                    Timber.d("created successfully")
                }
            }
        }
    }

}