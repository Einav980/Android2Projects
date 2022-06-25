package com.example.rently.ui.screens.apartments

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Watchlist
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.WatchlistRepository
import com.example.rently.ui.screens.apartments.events.ApartmentsFormEvent
import com.example.rently.ui.screens.apartments.state.ApartmentsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ApartmentsViewModel @Inject constructor(private val apartmentRepository: ApartmentRepository, private val watchListRepository: WatchlistRepository, private val datastore: DatastorePreferenceRepository):
    ViewModel() {
/*
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)
*/

    var state by mutableStateOf(ApartmentsScreenState())

    init {
        fetchApartments()
        fetchWatchlist()
    }

    fun onEvent(event: ApartmentsFormEvent){
        when(event){
            is ApartmentsFormEvent.AddToWatchlist -> {
                addToWatchList(event.apartmentId)
            }

            is ApartmentsFormEvent.RemoveFromWatchList -> {
                removeFromWatchlist(event.apartmentId)
            }

            is ApartmentsFormEvent.FilterApplied -> {

            }
        }
    }
//    fun listApartments(state: FilterFormState?) {
//        viewModelScope.launch {
//            isLoading.value = true
//            val response = repository.listApartments()
//            when(response){
//                is Resource.Success -> {
//                    if (state != null) {
//                        apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status &&
//                                apartment.numberOfBaths >= state.numberOfBathrooms + 1 &&
////                                apartment.numberOfRooms >= state.numberOfBedrooms + 1 &&
//                                apartment.price in (state.priceRange.toRange().lower * 10000).roundToInt()..
//                                (state.priceRange.toRange().upper * 10000).roundToInt() &&
//                                apartment.size >= (state.size * 150).roundToInt() &&
//                                isCityMatch(state.cities, apartment.city)
//
//                            //todo add filters for properties and apartment type
//                        })
//                    }else{
//                        apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status})
//                    }
//                }
//            }
//            isLoading.value = false
//        }
//    }

    private fun isCityMatch(citiesList: ArrayList<String>, city: String): Boolean {

        if (citiesList.isNotEmpty()) {
            return citiesList.contains(city)
        }
        return true
    }

    private fun fetchApartments() {
        viewModelScope.launch {
            val response = apartmentRepository.listApartments()
            when (response) {
                is Resource.Success -> {
                    state = state.copy(apartments = response.data!!)
                    state.apartments.forEach { apartment ->
                        Timber.tag("Rently").d("Apartments: " + apartment.location)
                    }
                }
            }
            state = state.copy(loading = false)
        }
    }

    private fun fetchWatchlist() {
        viewModelScope.launch {
            val userId = datastore.getUserEmail().first()
            val response = watchListRepository.listUserWatchlistItems(userId = userId)
            when (response) {
                is Resource.Success -> {
                    state = state.copy(userWatchlist = response.data!!)
                }
                else -> {
                    Log.d("Rently","Error fetching watchlist")
                }
            }
        }
    }

    private fun addToWatchList(apartmentId: String){
        viewModelScope.launch {
            val userEmail = datastore.getUserEmail().first()
            val watchlistItem = Watchlist(apartmentId = apartmentId, email = userEmail)
            val response = watchListRepository.addWatchListApartment(watchlistItem)
            when(response){
                is Resource.Success -> {
                    Log.d("Rently", "Added to watchlist")
                }

                else -> {

                }
            }
        }
    }

    private fun removeFromWatchlist(apartmentId: String){
        viewModelScope.launch {
            val userEmail = datastore.getUserEmail().first()
            val watchlistItem = Watchlist(apartmentId = apartmentId, email = userEmail)
            val response = watchListRepository.removeWatchListApartment(watchlistItem)
            when(response){
                is Resource.Success -> {

                }

                else -> {

                }
            }
        }
    }

}