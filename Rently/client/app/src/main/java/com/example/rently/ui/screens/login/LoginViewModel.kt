package com.example.rently.ui.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _invalidCredentialsErrorCode = 401
    private val _invalidUserErrorCode = 404
    private val _registeredSuccessful = 200
    val isLoggedIn = mutableStateOf(false)
    val isLoading = mutableStateOf(true)
    val isLoggingIn = mutableStateOf(false)
    val invalidCredentials = mutableStateOf(false)
    val errorOccurred = mutableStateOf(false)
    val isValidEmail = mutableStateOf(true)
    val isValidPassword = mutableStateOf(true)
    private val EmailRegex = Regex("^\\S+@\\S+\\.\\S+\$")

    fun loginUser(user: User) {
        viewModelScope.launch {
            validateData(user = user)
            if (isValidEmail.value && isValidPassword.value) {
                isLoggingIn.value = true
                val result = repository.loginUser(user)
                when (result) {
                    is Resource.Success -> {
                        val returnCode = result.data?.returnCode
                        if (returnCode == _registeredSuccessful) {
                            invalidCredentials.value = false
                            isLoggedIn.value = true
                        } else if (returnCode == _invalidCredentialsErrorCode || returnCode == _invalidUserErrorCode) {
                            invalidCredentials.value = true
                        }
                    }
                    else -> {
                        errorOccurred.value = true
                        Timber.d("Error while registering")
                    }
                }
                isLoggingIn.value = false
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