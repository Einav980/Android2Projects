package com.example.rently.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.ui.screens.login.events.LoginFormEvent
import com.example.rently.ui.screens.login.state.LoginFormState
import com.example.rently.validation.use_case.ValidateEmail
import com.example.rently.validation.use_case.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val datastore: DatastorePreferenceRepository,
    private val repository: UserRepository,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    companion object {
        const val LOGIN_SUCCESS = 200
    }

    var state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<LoginViewModel.ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is LoginFormEvent.Login -> {
                login()
            }
        }
    }

    private suspend fun loginUser(user: User) {
        validationEventChannel.send(ValidationEvent.LoginLoading)
        val result = repository.loginUser(user)
        when (result) {
            is Resource.Success -> {
                val returnCode = result.data?.returnCode
                if (returnCode == LOGIN_SUCCESS) {
                    delay(500)
                    validationEventChannel.send(ValidationEvent.LoginSuccess)
                    datastore.setLoggedIn(user.email)
                    state = state.copy(loginErrorMessage = null)
                    return
                } else {
                    validationEventChannel.send(ValidationEvent.LoginError)
                    state = state.copy(loginErrorMessage = "Invalid credentials")
                }
            }
            else -> {
                validationEventChannel.send(ValidationEvent.LoginError)
                state = state.copy(loginErrorMessage = "A network error has occurred")
            }
        }
    }

    private fun login() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val hasError =
            listOf(emailResult, passwordResult).any { !it.successful }

        if (hasError) {
            state = state.copy(emailError = emailResult.errorMessage, passwordError = passwordResult.errorMessage)
            return
        }

        val user = User(email = state.email, password = state.password)

        viewModelScope.launch {
            clearAllErrors()
            loginUser(user = user)
        }
    }

    private fun clearAllErrors() {
        state = state.copy(
            emailError = null,
            passwordError = null
        )
    }

    sealed class ValidationEvent {
        object LoginLoading : LoginViewModel.ValidationEvent()
        object LoginSuccess : LoginViewModel.ValidationEvent()
        object LoginError : LoginViewModel.ValidationEvent()
    }
}