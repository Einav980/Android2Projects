package com.example.rently.ui.screens.apartments

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Watchlist
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.WatchlistRepository
import com.example.rently.ui.screens.apartments.events.ApartmentsFormEvent
import com.example.rently.ui.screens.apartments.state.ApartmentsScreenState
import com.example.rently.ui.screens.manage_apartments.ManageApartmentsViewModel
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ApartmentsViewModel @Inject constructor(private val apartmentRepository: ApartmentRepository, private val watchListRepository: WatchlistRepository, private val datastore: DatastorePreferenceRepository):
    ViewModel() {

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = mutableStateOf(ApartmentsScreenState())
    val state: State<ApartmentsScreenState> = _state
    val watchlistApartment = mutableStateListOf<Watchlist>()


    init {
        fetchApartments()
        fetchWatchlist()
        Timber.tag("Rently").d("View model initiation")
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
            validationEventChannel.send(ValidationEvent.PageLoading)
            val response = apartmentRepository.listApartments()
            when (response) {
                is Resource.Success -> {
                    _state.value = state.value.copy(apartments = response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status })
                    validationEventChannel.send(ValidationEvent.PageLoaded)
                } else -> {
                    Log.d("Rently","Error fetching apartments")
                    validationEventChannel.send(ValidationEvent.PageError)
                }
            }
            _state.value = state.value.copy(loading = false)
        }
    }

    private fun fetchWatchlist() {
        viewModelScope.launch {
            val userId = datastore.getUserEmail().first()
            val response = watchListRepository.listUserWatchlistItems(userId = userId)
            when (response) {
                is Resource.Success -> {
                    watchlistApartment.addAll(response.data!!)
                    _state.value = state.value.copy(userWatchlist = watchlistApartment)
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
                    watchlistApartment.add(watchlistItem)
                    _state.value = state.value.copy(userWatchlist = watchlistApartment)
                    Log.d("Rently", "Added to watchlist")
                    validationEventChannel.send(ValidationEvent.AddApartmentToWatchlistSuccess)
                }
                else -> {
                    Log.d("Rently", "Error while Adding to watchlist")
                    validationEventChannel.send(ValidationEvent.AddApartmentToWatchlistError)
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
                    watchlistApartment.remove(watchlistItem)
                    _state.value = state.value.copy(userWatchlist = watchlistApartment)
                    Log.d("Rently", "Removed from watchlist")
                    validationEventChannel.send(ValidationEvent.RemoveApartmentToWatchlistSuccess)
                }
                else -> {
                    Log.d("Rently", "Error while removing from watchlist")
                    validationEventChannel.send(ValidationEvent.RemoveApartmentToWatchlistError)
                }
            }
        }
    }

    sealed class ValidationEvent {
        object PageLoading : ValidationEvent()
        object PageLoaded : ValidationEvent()
        object PageError : ValidationEvent()
        object AddApartmentToWatchlistSuccess : ValidationEvent()
        object AddApartmentToWatchlistError : ValidationEvent()
        object RemoveApartmentToWatchlistSuccess : ValidationEvent()
        object RemoveApartmentToWatchlistError : ValidationEvent()

    }

}