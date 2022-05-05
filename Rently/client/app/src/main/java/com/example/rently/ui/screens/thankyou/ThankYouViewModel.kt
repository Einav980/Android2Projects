package com.example.rently.ui.screens.thankyou

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThankYouViewModel @Inject constructor(): ViewModel(){
    val moveToLogin = mutableStateOf(false)
    fun landed(){
        viewModelScope.launch {
            delay(1500)
            moveToLogin.value = true
        }
    }
}