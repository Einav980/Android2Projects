package com.example.rently.ui.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.DatastorePreferenceRepository.Companion.LOGGED_IN
import com.example.rently.repository.UserRepository
import com.example.rently.repository.datastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val INVALID_CREDENTIALS = 401
    private val INVALID_USER = 404
    private val REGISTER_SUCCESSFULL = 200
    val isLoggedIn = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val invalidCredentials = mutableStateOf(false)
    val errorOccurred = mutableStateOf(false)
    val isValidEmail = mutableStateOf(true)
    val isValidPassword = mutableStateOf(true)
    private val EmailRegex = Regex("^\\S+@\\S+\\.\\S+\$")

    fun loginUser(user: User) {
        viewModelScope.launch {
            validateData(user = user)
            if (isValidEmail.value && isValidPassword.value) {
                isLoading.value = true
                val result = repository.loginUser(user)
                when (result) {
                    is Resource.Success -> {
                        val returnCode = result.data?.returnCode
                        if (returnCode == REGISTER_SUCCESSFULL) {
                            invalidCredentials.value = false
                            isLoggedIn.value = true
                        } else if (returnCode == INVALID_CREDENTIALS || returnCode == INVALID_USER) {
                            invalidCredentials.value = true
                        }
                    }
                    else -> {
                        errorOccurred.value = true
                        Timber.d("Error while registering")
                    }
                }
                isLoading.value = false
            }
        }
    }

    private fun validateEmail(email: String) {
        isValidEmail.value = email.matches(EmailRegex)
    }

    private fun validatePassword(password: String) {
        isValidPassword.value = password.isNotEmpty()
    }

    private fun validateData(user: User) {
        validateEmail(user.email)
        validatePassword(user.password)
    }
}