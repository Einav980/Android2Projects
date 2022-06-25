package com.example.rently.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.repository.WatchlistRepository
import com.example.rently.ui.screens.profile.events.ProfileFormEvent
import com.example.rently.ui.screens.profile.state.ProfileState
import com.example.rently.validation.use_case.ValidateFirstName
import com.example.rently.validation.use_case.ValidateLastName
import com.example.rently.validation.use_case.ValidatePhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val datastore: DatastorePreferenceRepository,
    private val userRepository: UserRepository,
    private val apartmentRepository: ApartmentRepository,
    private val watchListRepository: WatchlistRepository,
    private val validateFirstName: ValidateFirstName,
    private val validateLastName: ValidateLastName,
    private val validatePhone: ValidatePhone,
) :
    ViewModel() {

    var state by mutableStateOf(ProfileState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        getLoggedInUser()
        getLoggedInUserApartments()
        getLoggedInUserWatchlist()
    }

    fun onEvent(event: ProfileFormEvent) {
        when (event) {
            is ProfileFormEvent.Logout -> {
                logout()
            }
            is ProfileFormEvent.EditProfile -> {
                editUser()
            }
            is ProfileFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }
            is ProfileFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }
            is ProfileFormEvent.PhoneChanged -> {
                state = state.copy(phone = event.phone)
            }

        }
    }

    fun getLoggedInUser() {
        viewModelScope.launch {
            val userEmailResult = datastore.getUserEmail().first()
            if (userEmailResult.isNotEmpty()) {
                val response = userRepository.getUser(userEmailResult)
                when (response) {
                    is Resource.Success -> {
                        val data = response.data!!
                        state = state.copy(
                            userEmail = data.email,
                            lastName = data.lastname,
                            firstName = data.firstname,
                            phone = data.phone,
                            headFirstname = data.firstname,
                            headLastname = data.lastname
                        )
                    }
                    else -> {
                        Log.d("Rently", "could not find user")
                    }
                }
            }
        }
    }

    private fun getLoggedInUserApartments() {
        viewModelScope.launch {
            val userEmailResult = datastore.getUserEmail().first()
            if (userEmailResult.isNotEmpty()) {
                val response = apartmentRepository.listUserApartments(userEmailResult)
                when (response) {
                    is Resource.Success -> {
                        state = state.copy(userApartments = response.data!!.size)
                    }
                    else -> {
                        Log.d("Rently", "could not find user")
                    }
                }
            }
        }
    }

    private fun getLoggedInUserWatchlist() {
        viewModelScope.launch {
            val userEmailResult = datastore.getUserEmail().first()
            if (userEmailResult.isNotEmpty()) {
                val response = watchListRepository.listUserWatchListApartments(userEmailResult)
                when (response) {
                    is Resource.Success -> {
                        state = state.copy(userWatchlist = response.data!!.size)
                    }
                    else -> {
                        Log.d("Rently", "could not find user")
                    }
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            datastore.setLoggedOut()
        }
    }

    private fun editTextFields() {
        if (state.editableText) {
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.EditLoading)
                val response = userRepository.editUser(
                    state.userEmail,
                    User(
                        firstname = state.firstName,
                        lastname = state.lastName,
                        phone = state.phone
                    )
                )
                when (response) {
                    is Resource.Success -> {
                        state= state.copy(editableText = false)
                        updateHeadName()
                        validationEventChannel.send(ValidationEvent.EditSuccess)
                        Log.d("Rently", "Edit user successfully \n ${response}")
                    }
                    else -> {
                        validationEventChannel.send(ValidationEvent.EditError)
                        Log.d("Rently", "could not edit user")
                    }
                }

            }
        } else {
            state= state.copy(editableText = true)
        }
    }

    private fun editUser() {
        val firstNameResult = validateFirstName.execute(state.firstName)
        val lastNameResult = validateLastName.execute(state.lastName)
        val phoneResult = validatePhone.execute(state.phone)

        val hasError = listOf(
            firstNameResult,
            lastNameResult,
            phoneResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                phoneError = phoneResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            clearErrors()
            editTextFields()
        }
    }

    private fun clearErrors() {
        state = state.copy(
            firstNameError = null,
            lastNameError = null,
            phoneError = null,
        )
    }



    private fun updateHeadName() {
        viewModelScope.launch {
            state = state.copy(headLastname = state.lastName , headFirstname = state.firstName)
        }
    }

    fun isUserHeadNotEmpty(): Boolean {
        return state.headLastname.isNotEmpty() && state.headFirstname.isNotEmpty()
    }

    sealed class ValidationEvent {
        object EditLoading : ValidationEvent()
        object EditSuccess : ValidationEvent()
        object EditError : ValidationEvent()
    }
}
