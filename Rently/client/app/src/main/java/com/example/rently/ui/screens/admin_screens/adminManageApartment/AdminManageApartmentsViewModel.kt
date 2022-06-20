package com.example.rently.ui.screens.admin_screens.adminManageApartment

import android.util.Log
import android.widget.Switch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.UserRepository
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AdminManageApartmentsViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepository,
) : ViewModel() {
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)

    fun listPendingApartments() {
        viewModelScope.launch {
            isLoading.value = true
            val response = apartmentRepository.listApartments()
            when (response) {
                is Resource.Success -> {
                    apartments.addAll(response.data!!.filter { apartment -> apartment.status != ApartmentStatus.Closed.status })
                }
                else -> {
                    Log.d("Rently", "could not find user apartments")
                }
            }
            isLoading.value = false
        }
    }

    fun onApproveSwipe(apartment: Apartment) {
        viewModelScope.launch {
            isLoading.value = true
            if (apartment.status != ApartmentStatus.Available.status){
                val apartmentId = apartment._id
                val newApartmentStatus = ApartmentStatus.Available.status
                apartment.status = newApartmentStatus
                val response =
                    apartmentRepository.editApartment(id = apartmentId, apartment = apartment)
                when (response) {
                    is Resource.Success -> {
                        val apartmentIndex= apartments.indexOf(apartment)
                        apartments.removeAt(apartmentIndex)
                        apartments.add(apartmentIndex , apartment)
                        Log.d("Rently", "Apartment status changed successfully")
                    }
                    else -> {
                        Log.d("Rently", "could not change the apartment status")
                    }
                }
            }
            isLoading.value = false
        }
    }

    fun onRejectSwipe(apartment: Apartment) {
        viewModelScope.launch {
            isLoading.value = true
            if (apartment.status != ApartmentStatus.Rejected.status){
                val apartmentId = apartment._id
                val newApartmentStatus = ApartmentStatus.Rejected.status
                apartment.status = newApartmentStatus
                val response =
                    apartmentRepository.editApartment(id = apartmentId, apartment = apartment)
                when (response) {
                    is Resource.Success -> {
                        val apartmentIndex= apartments.indexOf(apartment)
                        apartments.removeAt(apartmentIndex)
                        apartments.add(apartmentIndex , apartment)
                        Log.d("Rently", "Apartment status changed successfully")
                    }
                    else -> {
                        Log.d("Rently", "could not change the apartment status")
                    }
                }
            }
            isLoading.value = false
        }
    }
}