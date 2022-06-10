package com.example.rently

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rently.model.Apartment

class SharedViewModel: ViewModel() {
    var apartment by mutableStateOf<Apartment?>(null)
        private set

    @JvmName("setApartment1")
    fun setApartment(newApartment: Apartment){
        apartment = newApartment
    }
}