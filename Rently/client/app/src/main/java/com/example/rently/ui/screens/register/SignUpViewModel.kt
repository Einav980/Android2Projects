package com.example.rently.ui.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    val isLoggedIn = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val registeredSuccessfully = mutableStateOf(false)

    fun registerUser(user: User){
        viewModelScope.launch {
            isLoading.value = true
            delay(2000)
            val result = repository.signUpUser(user)
            when(result){
                is Resource.Success -> {
                    Log.d("Response","Registered successfully!")
                    registeredSuccessfully.value = true
                }
                else -> { Log.d("Response", "Error while registering")}
            }
            isLoading.value = false
        }
    }
}