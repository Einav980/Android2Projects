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
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {


    val isLoggedIn = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val invalidCredentials = mutableStateOf(false)
    val showInvalidCredentialsDialog = mutableStateOf(false)
    val isValidEmail = mutableStateOf(true)
    val isValidPassword = mutableStateOf(true)
    private val EmailRegex =  Regex("^\\S+@\\S+\\.\\S+\$")

    fun loginUser(user: User){
        viewModelScope.launch {
            validateData(user = user)
            if(isValidEmail.value && isValidPassword.value){
                isLoading.value = true
                val response = repository.loginUser(user)
                when(response){
                    is Resource.Success -> {

                    }

                    is Resource.Error -> {
                        isLoggedIn.value = false
                        invalidCredentials.value = true
                    }

                }
                isLoading.value = false
            }
        }
    }

    private fun validateEmail(email: String){
        isValidEmail.value = email.matches(EmailRegex)
    }

    private fun validatePassword(password: String){
        isValidPassword.value = password.isNotEmpty()
    }

    private fun validateData(user: User){
        validateEmail(user.email)
        validatePassword(user.password)
    }
}