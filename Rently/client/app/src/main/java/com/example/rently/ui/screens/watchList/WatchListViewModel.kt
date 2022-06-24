package com.example.rently.ui.screens.watchList

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val ApartmentRepository: ApartmentRepository, private val datastore: DatastorePreferenceRepository) :
    ViewModel() {
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)


    fun watchListApartments() {
        viewModelScope.launch {
            isLoading.value = true
            val userEmailResult = datastore.getUserEmail().first()
            val response = ApartmentRepository.listApartments() // todo create WatchList API and create new table in DB
            when (response) {
                is Resource.Success -> {
                    apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status })

                }
            }
            isLoading.value = false
        }
    }

}