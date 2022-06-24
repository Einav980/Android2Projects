package com.example.rently.ui.screens.watchList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.Watchlist
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.WatchListRepository
import com.example.rently.ui.screens.watchList.state.WatchlistState
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(private val watchListRepository: WatchListRepository, private val dataRepository : DatastorePreferenceRepository) :
    ViewModel() {
    var state by mutableStateOf(WatchlistState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        fetchWatchlistApartments()
    }


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
            val watchList = Watchlist(email = userEmailResult.value, apartmentId = apartment._id )
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

    private fun fetchWatchlistApartments(){
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.PageLoading)
            val userEmail = dataRepository.getUserEmail().first()
            state = state.copy(userEmail = userEmail)
            val response = watchListRepository.listUserWatchListApartments(state.userEmail)
            when (response) {
                is Resource.Success -> {
                    /* TODO: get only 'available' apartments*/
                    state = state.copy(apartments = response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status } )
                    validationEventChannel.send(ValidationEvent.PageSuccess)
                }
                else -> {
                    validationEventChannel.send(ValidationEvent.PageError)
                }
            }
        }
    }

    sealed class ValidationEvent {
        object PageLoading : ValidationEvent()
        object PageError : ValidationEvent()
        object PageSuccess : ValidationEvent()
    }
}