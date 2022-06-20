package com.example.rently.ui.screens.main

import android.content.Context
import android.util.Log
import android.util.Log.INFO
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.repository.UserRepository
import com.example.rently.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.logging.Level.INFO
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    var userType = mutableStateOf(UserType.Normal)


    fun logoutUser() {
        viewModelScope.launch {
            val result = repository.logoutUser()
            when (result) {
                is Resource.Success -> {
                    if(result.data == true){
                        Timber.i("logout success")
                    }
                }
                else -> {
                    Timber.i("logout failed")
                }
            }
        }
    }

    fun getLoggedInUserPermissions() {
        viewModelScope.launch {
            val userEmailResult = repository.getUserEmail().first()
            if (userEmailResult.isNotEmpty()){
                val response = repository.getUser(userEmailResult)
                when(response){
                    is Resource.Success -> {
                        val data =  response.data!!
                        userType.value = UserType.valueOf(data.type)
                    }
                    else -> {
                        Log.d("Rently", "could not find user")}
                }
            }
        }
    }
}