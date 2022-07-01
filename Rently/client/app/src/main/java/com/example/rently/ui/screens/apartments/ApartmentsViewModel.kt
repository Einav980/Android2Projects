package com.example.rently.ui.screens.apartments

import android.util.Log
import androidx.compose.runtime.*
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Watchlist
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.WatchlistRepository
import com.example.rently.ui.screens.apartments.events.ApartmentsFormEvent
import com.example.rently.ui.screens.apartments.state.ApartmentsScreenState
import com.example.rently.ui.screens.filter.state.FilterFormState
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ApartmentsViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepository,
    private val watchListRepository: WatchlistRepository,
    private val datastore: DatastorePreferenceRepository
) :
    ViewModel() {

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = mutableStateOf(ApartmentsScreenState())
    val state: State<ApartmentsScreenState> = _state

    private val _filterState = mutableStateOf(FilterFormState())
    val filterState: State<FilterFormState> = _filterState

    val watchlistApartment = mutableStateListOf<Watchlist>()

    val selectedApartmentTypesIndexes = mutableStateListOf<Int>()

    init {
        fetchApartments()
        fetchApartmentTypes()
        fetchWatchlist()
        Timber.tag("Rently").d("View model initiation")
    }

    fun onEvent(event: ApartmentsFormEvent) {
        when (event) {
            is ApartmentsFormEvent.AddToWatchlist -> {
                addToWatchList(event.apartmentId)
            }

            is ApartmentsFormEvent.RemoveFromWatchList -> {
                removeFromWatchlist(event.apartmentId)
            }

            is ApartmentsFormEvent.ApplyFilter -> {
                applyFilter()
            }

            is ApartmentsFormEvent.ClearFilter -> {
                clearFilter()
            }

            is ApartmentsFormEvent.CitiesChanged -> {
                _filterState.value = _filterState.value.copy(cities = event.cities)
            }

            is ApartmentsFormEvent.PriceRangeChanged -> {
                _filterState.value = _filterState.value.copy(priceRange = event.priceRange)
            }

            is ApartmentsFormEvent.SizeChanged -> {
                _filterState.value = _filterState.value.copy(size = event.size)
            }

            is ApartmentsFormEvent.NumberOfBathroomsChanged -> {
                _filterState.value = _filterState.value.copy(numberOfBathrooms = event.amount)
            }

            is ApartmentsFormEvent.PropertyTypesChanged -> { //todo fix the property type in the ui
                _filterState.value = _filterState.value.copy(selectedApartmentTypesIndexes = event.types)
            }

            is ApartmentsFormEvent.NumberOfBedroomsChanged -> {
                _filterState.value = _filterState.value.copy(numberOfBedrooms = event.amount)
            }

            is ApartmentsFormEvent.HasBalconyChanged -> {
                _filterState.value = _filterState.value.copy(hasBalcony = event.checkState)
            }

            is ApartmentsFormEvent.HasParkingChanged -> {
                _filterState.value = _filterState.value.copy(hasParking = event.checkState)
            }

            is ApartmentsFormEvent.IsAnimalFriendlyChanged -> {
                _filterState.value = _filterState.value.copy(isAnimalFriendly = event.checkState)
            }

            is ApartmentsFormEvent.IsFurnishedChanged -> {
                _filterState.value = _filterState.value.copy(isFurnished = event.checkState)
            }
        }
    }

    private fun applyFilter() {
        _state.value = _state.value.copy(isFiltered = true)
        fetchApartments(_filterState.value)
    }

    private fun isCityMatch(citiesList: ArrayList<String>, city: String): Boolean {

        if (citiesList.isNotEmpty()) {
            return citiesList.contains(city)
        }
        return true
    }

    private fun fetchApartments(filter: FilterFormState? = null) {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.PageLoading)
            val response = apartmentRepository.listApartments()
            when (response) {
                is Resource.Success -> {
                    if(filter != null){
                        val filteredApartments = response.data!!.filter { apartment ->
                            apartment.status == ApartmentStatus.Available.status &&
                                    apartment.numberOfBaths >= filter.numberOfBathrooms &&
                                    apartment.numberOfBeds >= filter.numberOfBedrooms &&
                                    apartment.price in (filter.priceRange.toRange().lower * 10000).roundToInt()..(filter.priceRange.toRange().upper * 10000).roundToInt() &&
                                    apartment.size >= (filter.size * 150).roundToInt() &&
                                    isCityMatch(filter.cities, apartment.city)
                        }

                        _state.value = _state.value.copy(apartments = filteredApartments)
                    }
                    else{
                        _state.value = _state.value.copy(apartments = response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status })
                    }

                    validationEventChannel.send(ValidationEvent.PageLoaded)
                }
                else -> {
                    Log.d("Rently", "Error fetching apartments")
                    validationEventChannel.send(ValidationEvent.PageError)
                }
            }
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
                    Log.d("Rently", "Error fetching watchlist")
                }
            }
        }
    }

    private fun addToWatchList(apartmentId: String) {
        viewModelScope.launch {
            val userEmail = datastore.getUserEmail().first()
            val watchlistItem = Watchlist(apartmentId = apartmentId, email = userEmail)
            val response = watchListRepository.addWatchListApartment(watchlistItem)
            when (response) {
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

    private fun removeFromWatchlist(apartmentId: String) {
        viewModelScope.launch {
            val userEmail = datastore.getUserEmail().first()
            val watchlistItem = Watchlist(apartmentId = apartmentId, email = userEmail)
            val response = watchListRepository.removeWatchListApartment(watchlistItem)
            when (response) {
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

    private fun clearFilter() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isFiltered = false)
            _filterState.value = FilterFormState()
            fetchApartments()
            fetchApartmentTypes()
            _filterState.value = _filterState.value.copy(selectedApartmentTypesIndexes = ArrayList())
        }
    }

    private fun fetchApartmentTypes() {
        viewModelScope.launch {
            _filterState.value = _filterState.value.copy(isApartmentTypesLoading = true)
            val response = apartmentRepository.listApartmentTypes()
            when (response) {
                is Resource.Success -> {
                    _filterState.value = _filterState.value.copy(apartmentTypes = response.data!!)
                }
                else -> {
                    Log.d("Rently", "Error listing apartment types")
                }
            }
            _filterState.value = _filterState.value.copy(isApartmentTypesLoading = false)
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