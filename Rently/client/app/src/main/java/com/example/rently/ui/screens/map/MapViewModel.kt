package com.example.rently.ui.screens.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.repository.ApartmentRepository
import com.example.rently.ui.screens.map.events.MapEvent
import com.example.rently.ui.screens.map.state.MapState
import com.example.rently.ui.screens.profile.ProfileScreenViewModel
import com.google.android.gms.maps.model.CameraPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    val apartmentRepository: ApartmentRepository,
) : ViewModel() {

    var state by mutableStateOf(MapState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        fetchApartments()
    }

    fun onEvent(event: MapEvent){
        when(event){
            is MapEvent.SelectedApartmentChanged -> {
                selectApartment(apartment = event.apartment)
            }
        }
    }

    private fun fetchApartments() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.LoadingApartments)
            val response = apartmentRepository.listApartments()
            when (response) {
                is Resource.Success -> {
                    state = state.copy(apartments = response.data!!)
                    validationEventChannel.send(ValidationEvent.LoadingFinished)
                }
                else -> {
                    validationEventChannel.send(ValidationEvent.LoadingError)
                }
            }
        }
    }

    private fun selectApartment(apartment: Apartment){
        viewModelScope.launch {
            state = state.copy(selectedApartment = apartment)
            validationEventChannel.send(ValidationEvent.ShowSelectedApartment)
        }
    }

    sealed class ValidationEvent{
        object LoadingApartments: ValidationEvent()
        object LoadingFinished: ValidationEvent()
        object LoadingError: ValidationEvent()
        object ShowSelectedApartment: ValidationEvent()
    }

}