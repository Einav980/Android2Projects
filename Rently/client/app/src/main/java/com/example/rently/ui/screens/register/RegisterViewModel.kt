package com.example.rently.ui.screens.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.ui.screens.register.events.RegisterFormEvent
import com.example.rently.ui.screens.register.state.RegisterFormState
import com.example.rently.validation.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val datastore: DatastorePreferenceRepository,
    private val repository: UserRepository,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidateRegisterPassword,
    private val validateFirstName: ValidateFirstName,
    private val validateLastName: ValidateLastName,
    private val validatePhone: ValidatePhone,
) : ViewModel() {

    companion object{
        private const val EMAIL_IN_USE = 405
        private const val REGISTER_SUCCESS = 201
    }

    var state by mutableStateOf(RegisterFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is RegisterFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is RegisterFormEvent.FirstNameChanged -> {
                state = state.copy(firstName = event.firstName)
            }

            is RegisterFormEvent.LastNameChanged -> {
                state = state.copy(lastName = event.lastName)
            }

            is RegisterFormEvent.PhoneChanged -> {
                if(event.phone.length < 11){
                    state = state.copy(phone = event.phone)
                }
            }

            is RegisterFormEvent.Register -> {
                Log.d("Rently", "Registration event")
                register()
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.RegisterLoading)
            val user = User(
                email = state.email,
                password = state.password,
                firstname = state.firstName,
                lastname = state.lastName,
                phone = state.phone
            )
            val result = repository.registerUser(user)
            when (result) {
                is Resource.Success -> {
                    val returnCode = result.data?.returnCode
                    if (returnCode == REGISTER_SUCCESS) {
                        Log.d("Rently", "Registered successfully!")
                        state = state.copy(registerErrorMessage = null)
                        datastore.setLoggedIn(state.email)
                        validationEventChannel.send(ValidationEvent.RegisterSuccess)
                    } else if (returnCode == EMAIL_IN_USE) {
                        state = state.copy(registerErrorMessage = "Email is already in use")
                        validationEventChannel.send(ValidationEvent.RegisterError)
                    }
                    else{
                        state = state.copy(registerErrorMessage = result.message)
                        validationEventChannel.send(ValidationEvent.RegisterError)
                    }
                }
                else -> {
                    state = state.copy(registerErrorMessage = "Error while registering")
                    validationEventChannel.send(ValidationEvent.RegisterError)
                }
            }
        }
    }

    private fun register() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val firstNameResult = validateFirstName.execute(state.firstName)
        val lastNameResult = validateLastName.execute(state.lastName)
        val phoneResult = validatePhone.execute(state.phone)

        val hasError = listOf(
            emailResult,
            passwordResult,
            firstNameResult,
            lastNameResult,
            phoneResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                phoneError = phoneResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            clearErrors()
            registerUser()
        }
    }

    private fun clearErrors() {
        state = state.copy(
            emailError = null,
            passwordError = null,
            firstNameError = null,
            lastNameError = null,
            phoneError = null,
        )
    }

    sealed class ValidationEvent {
        object RegisterLoading : ValidationEvent()
        object RegisterSuccess : ValidationEvent()
        object RegisterError : ValidationEvent()
    }
}
