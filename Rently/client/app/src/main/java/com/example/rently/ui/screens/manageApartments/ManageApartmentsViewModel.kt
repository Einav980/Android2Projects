package com.example.rently.ui.screens.manageApartments

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManageApartmentsViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)

    fun listUserApartments() {
        viewModelScope.launch {
            isLoading.value = true
            val userEmailResult = userRepository.getUserEmail().first()
            if (userEmailResult.isNotEmpty()) {
                val response = apartmentRepository.listUserApartments(userEmailResult)
                when (response) {
                    is Resource.Success -> {
                        apartments.addAll(response.data!!)
                    }
                    else -> {
                    Log.d("Rently", "could not find user apartments")}
                }
            }

            isLoading.value = false
        }
    }

    fun deleteApartment(apartment: Apartment) {
        viewModelScope.launch {
            isLoading.value = true
            val apartmentId = apartment._id
            val response = apartmentRepository.deleteApartment(apartmentId)
            when (response) {
                is Resource.Success -> {
                    apartments.remove(apartment)
                    Log.d("Rently", "Apartment deleted successfully")
                }
                else -> {
                    Log.d("Rently", "could not delete apartment")}
            }
            isLoading.value = false
        }
    }

}