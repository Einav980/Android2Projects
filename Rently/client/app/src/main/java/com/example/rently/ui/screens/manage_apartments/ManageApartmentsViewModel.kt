package com.example.rently.ui.screens.manage_apartments

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.ui.screens.manage_apartments.events.ManageApartmentsFormEvent
import com.example.rently.ui.screens.manage_apartments.state.ManageApartmentsState
import com.example.rently.util.ApartmentStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageApartmentsViewModel @Inject constructor(
    private val datastore: DatastorePreferenceRepository,
    private val apartmentRepository: ApartmentRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(ManageApartmentsState())

    var apartments = mutableStateListOf<Apartment>()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        listUserApartments()
    }

    fun onEvent(event: ManageApartmentsFormEvent) {
        when (event) {
            is ManageApartmentsFormEvent.ApartmentDeleted -> {
                deleteApartment(event.apartment)
            }

            is ManageApartmentsFormEvent.ApartmentStatusChanged -> {
                changeApartmentStatus(event.apartment)
            }
        }
    }

    private fun listUserApartments() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.PageLoading)
            val userEmailResult = datastore.getUserEmail().first()
            if (userEmailResult.isNotEmpty()) {
                val response = apartmentRepository.listUserApartments(userEmailResult)
                when (response) {
                    is Resource.Success -> {
                        apartments.addAll(response.data!!)
                        state = state.copy(apartments = response.data!!)
                        validationEventChannel.send(ValidationEvent.PageLoaded)
                    }
                    else -> {
                        validationEventChannel.send(ValidationEvent.PageError)
                        Log.d("Rently", "could not find user apartments")
                    }
                }
            }
        }
    }

    private fun deleteApartment(apartment: Apartment) {
        viewModelScope.launch {
            val apartmentId = apartment._id
            val response = apartmentRepository.deleteApartment(apartmentId)
            when (response) {
                is Resource.Success -> {
                    state.apartments.remove(apartment)
                    state = state.copy(apartments = state.apartments)
                    validationEventChannel.send(ValidationEvent.ApartmentDeleteSuccess)
                    Log.d("Rently", "Apartment deleted successfully")
                }
                else -> {
                    validationEventChannel.send(ValidationEvent.ApartmentDeleteError)
                    Log.d("Rently", "could not delete apartment")
                }
            }
        }
    }

    private fun changeApartmentStatus(apartment: Apartment) {
        viewModelScope.launch {
            val apartmentId = apartment._id
            val newApartmentStatus =
                if (apartment.status == ApartmentStatus.Available.status) ApartmentStatus.Closed else ApartmentStatus.Available
            apartment.status = newApartmentStatus.status
            val response =
                apartmentRepository.editApartment(id = apartmentId, apartment = apartment)
            when (response) {
                is Resource.Success -> {
                    val apartmentIndex = state.apartments.indexOf(apartment)
                    state.apartments.removeAt(apartmentIndex)
                    state.apartments.add(apartmentIndex, apartment)
                    state = state.copy(apartments = state.apartments)
                    Log.d("Rently", "Apartment status changed successfully")
                    validationEventChannel.send(ValidationEvent.ApartmentStatusChangeSuccess)
                }
                else -> {
                    validationEventChannel.send(ValidationEvent.ApartmentStatusChangeError)
                    Log.d("Rently", "could not change the apartment status")
                }
            }
        }
    }

    sealed class ValidationEvent {
        object PageLoading : ValidationEvent()
        object PageLoaded : ValidationEvent()
        object PageError : ValidationEvent()
        object ApartmentDeleteSuccess : ValidationEvent()
        object ApartmentDeleteError : ValidationEvent()
        object ApartmentStatusChangeError : ValidationEvent()
        object ApartmentStatusChangeSuccess: ValidationEvent()
    }

}