package com.example.rently.ui.screens.main

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.util.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val repository: UserRepository, private val datastore: DatastorePreferenceRepository): ViewModel() {

    var userType = mutableStateOf(UserType.Normal)

    fun getLoggedInUserPermissions() {
        viewModelScope.launch {
            val userEmailResult = datastore.getUserEmail().first()
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