package com.example.rently.ui.screens.splash

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    val isLoggedIn = mutableStateOf(false)

    fun checkIfUserLoggedIn() {
        viewModelScope.launch {
            val result = repository.checkIfUserIsLoggedIn()
            when (result) {
                is Resource.Success -> {
                    if(result.data == true){
                        isLoggedIn.value = true
                    }
                }
                else -> {
                    isLoggedIn.value = false
                }
            }
        }
    }
}