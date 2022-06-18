package com.example.rently.ui.screens.add_apartment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.ApartmentType
import com.example.rently.model.google.GooglePrediction
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.GooglePlacesRepository
import com.example.rently.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class AddApartmentViewModel @Inject constructor(
    private val googleRepository: GooglePlacesRepository,
    private val imagesRepository: ImagesRepository,
    private val apartmentRepository: ApartmentRepository
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val showPredictions = mutableStateOf(false)
    var predictions = mutableStateListOf<GooglePrediction>()
    private var apartmentTypes = mutableStateListOf<ApartmentType>()

    private fun getPredictions(address: String) {
        showPredictions.value = true
        viewModelScope.launch {
            isLoading.value = true
            val response = googleRepository.getPredictions(input = address)
            when (response) {
                is Resource.Success -> {
                    predictions = response?.data?.predictions!!.toMutableStateList()
                }
            }

            isLoading.value = false
        }
    }

    fun showPredictions() {
        showPredictions.value = true
    }

    fun hidePredictions() {
        showPredictions.value = false
    }

    fun onSearchAddressChange(address: String) {
        if(address.isNotEmpty()){
            getPredictions(address)
            showPredictions()
        }
        else{
            hidePredictions()
        }
    }

    fun listApartmentTypes(){
        viewModelScope.launch {
            val response = apartmentRepository.listApartmentTypes()
            when(response){
                is Resource.Success -> {
                    apartmentTypes = response?.data?.toMutableStateList()!!
                }
            }
        }
    }

    fun uploadImage(imageBase64: String) {
        viewModelScope.launch {
            val response = imagesRepository.uploadImage(image = imageBase64)
            when (response) {
                is Resource.Success -> {
                    Log.d("Rently", "${response.data?.data?.url}")
                    Log.d("Rently", "Uploaded successfully!")
                }
                else -> {
                    Log.d("Rently", "Error while uploading image")
                }
            }
        }
    }

    fun getAddressLocation(address: String){
        viewModelScope.launch {
            val response = googleRepository.getAddressLocation(address = address)
            when(response){
                is Resource.Success -> {
                    Log.d("Rently", "Address location: ${response.data?.results!![0].geometry}")
                }
                else -> {
                    Log.d("Rently", "Error has occured")
                }
            }
        }

    }
}