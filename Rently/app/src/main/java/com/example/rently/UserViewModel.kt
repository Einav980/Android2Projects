package com.example.rently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val auth: UserRepository): ViewModel() {

    fun registerUser(user: User){
        viewModelScope.launch {
            val response = auth.signUpUser(user)
        }
    }


}