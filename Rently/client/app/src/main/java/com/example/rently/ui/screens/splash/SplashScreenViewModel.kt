package com.example.rently.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.repository.DatastorePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val datastore: DatastorePreferenceRepository): ViewModel() {

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        validateUserLoggedIn()
    }

    private fun validateUserLoggedIn(){
        viewModelScope.launch {
            val loggedIn = datastore.isLoggedIn()
            if(loggedIn){
                validationEventChannel.send(ValidationEvent.LoggedIn)
            }
            else{
                validationEventChannel.send(ValidationEvent.NotLoggedIn)
            }
        }
    }

    sealed class ValidationEvent {
        object LoggedIn : SplashScreenViewModel.ValidationEvent()
        object NotLoggedIn : SplashScreenViewModel.ValidationEvent()
    }
}