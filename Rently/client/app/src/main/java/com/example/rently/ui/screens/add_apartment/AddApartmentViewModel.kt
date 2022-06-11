package com.example.rently.ui.screens.add_apartment

import android.gesture.Prediction
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.GooglePrediction
import com.example.rently.repository.GooglePlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddApartmentViewModel @Inject constructor(private val googleRepository: GooglePlacesRepository): ViewModel(){

    val isLoading = mutableStateOf(false)
    val showPredictions = mutableStateOf(false)
    val predictions = mutableStateOf(ArrayList<GooglePrediction>())

    private fun getPredictions(address: String) {
        showPredictions.value = true
        viewModelScope.launch {
            isLoading.value = true
            val response = googleRepository.getPredictions(input = address)
            when(response){
                is Resource.Success -> {
                    predictions.value = response.data?.predictions!!
                }
            }

            isLoading.value = false
        }
    }

    fun showPredictions(){
        showPredictions.value = true
    }

    fun hidePredictions(){
        showPredictions.value = false
    }

    fun onSearchAddressChange(address: String){
        getPredictions(address)
        showPredictions()
    }
}