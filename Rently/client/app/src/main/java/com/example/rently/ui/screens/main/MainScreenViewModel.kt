package com.example.rently.ui.screens.main

import android.content.Context
import android.util.Log
import android.util.Log.INFO
import android.widget.Toast
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph
import com.example.rently.Resource
import com.example.rently.navigation.Screen
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.logging.Level.INFO
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    fun logoutUser() {
        viewModelScope.launch {
            val result = repository.logoutUser()
            when (result) {
                is Resource.Success -> {
                    if(result.data == true){
                        Screen.Login.route
                    }
                }
                else -> {
                    Timber.i("logout failed")
                }
            }
        }
    }
}