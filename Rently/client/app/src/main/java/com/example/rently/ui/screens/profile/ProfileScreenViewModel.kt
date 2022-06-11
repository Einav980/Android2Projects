package com.example.rently.ui.screens.profile

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {

    val email = mutableStateOf("")
    val lastname = mutableStateOf("")
    val firstname = mutableStateOf("")
    val phone = mutableStateOf("")


    val user = mutableStateOf(User())

    fun isUserNotEmpty(): Boolean{
        return email.value.isNotEmpty() && phone.value.isNotEmpty() && lastname.value.isNotEmpty() && firstname.value.isNotEmpty()
    }

    fun onEmailChange(value: String){
        email.value = value
    }
    fun onPhoneChange(value: String){
        phone.value = value
    }
    fun onLastNameChange(value: String){
        lastname.value = value
    }
    fun onFirstNameChange(value: String){
        firstname.value = value
    }


    fun getLoggedInUser() {
        viewModelScope.launch {
            val userEmailResult = repository.getUserEmail().first()
            if (userEmailResult.isNotEmpty()){
                val response = repository.getUser(userEmailResult)
                when(response){
                    is Resource.Success -> {
                        val data =  response.data!!
                        email.value = data.email
                        lastname.value = data.lastName
                        firstname.value = data.firstName
                        phone.value = data.phone
                    }
                    else -> {
                        Log.d("Rently", "could not find user")}
                }
            }
        }
    }
}
