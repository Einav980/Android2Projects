package com.example.rently.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    val isLoggedIn = mutableStateOf(false)

    fun loginUser(user: User){
        viewModelScope.launch {
            val result = repository.loginUser(user)
            when(result){
                is Resource.Success -> {
                    isLoggedIn.value = true
                }

                is Resource.Error -> {
                    isLoggedIn.value = false
                }
            }
        }
    }
}