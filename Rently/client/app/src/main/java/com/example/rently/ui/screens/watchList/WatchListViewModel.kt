package com.example.rently.ui.screens.watchList

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.UserRepository
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val watchListRepository: WatchListRepository, private val dataRepository : DatastorePreferenceRepository) :
    ViewModel() {
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)
    var userEmailResult = mutableStateOf("")


    fun watchListApartments() {
        viewModelScope.launch {
            isLoading.value = true
            userEmailResult.value = dataRepository.getUserEmail().first()
            val response = watchListRepository.listUserWatchListApartments(userEmailResult.value)
            when (response) {
                is Resource.Success -> {
                    apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status })
                }
            }
            isLoading.value = false
        }
    }

    fun removeWatchListApartments(apartment: Apartment) {
        viewModelScope.launch {
            isLoading.value = true
            val watchList = WatchList(email = userEmailResult.value, apartmentId = apartment._id )
            val response = watchListRepository.removeWatchListApartment(watchList)
            when (response) {
                is Resource.Success -> {
                    apartments.remove(apartment)
                    Log.d("Rently", "Apartment removed from watchlist successfully")
                }
                else -> {
                    Log.d("Rently", "could not remove apartment from watchlist")}
            }
            isLoading.value = false
        }
    }

}