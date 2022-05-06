package com.example.rently.ui.screens.single_apartment

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import com.example.rently.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class SingleApartmentViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    val currentApartment = mutableStateOf(null)
    val isLoading = mutableStateOf(true)
    fun getUserInfo(id: String){
        viewModelScope.launch {
            val response = repository.getUser(id)
            when(response){
                is Resource.Success -> {
                    if(response != null){
                        Log.d("Response", response.data.toString())
                    }
                }
            }
        }
    }
}