package com.example.rently.ui.screens.single_apartment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.repository.UserRepository
import com.example.rently.ui.screens.single_apartment.events.SingleApartmentEvent
import com.example.rently.ui.screens.single_apartment.state.SingleApartmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel

class SingleApartmentViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    var state by mutableStateOf(SingleApartmentState())

    fun onEvent(event: SingleApartmentEvent){
        when(event){
            is SingleApartmentEvent.ApartmentChanged -> {
                getApartmentUserInfo(event.apartment.userId)
            }
        }
    }

    fun getApartmentUserInfo(userId: String){
        viewModelScope.launch {
            val response = repository.getUser(userId)
            when(response){
                is Resource.Success -> {
                    state = state.copy(user = response.data!!)
                }
            }
        }
    }
}