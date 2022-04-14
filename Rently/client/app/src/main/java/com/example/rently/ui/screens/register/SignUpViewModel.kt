package com.example.rently.ui.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val EMAIL_IS_IN_USE = 405
    private val REGISTER_SUCCESSFULL = 201
    private val EmailRegex =  Regex("^\\S+@\\S+\\.\\S+\$")

    val isLoading = mutableStateOf(false)
    val registeredSuccessfully = mutableStateOf(false)
    val emailInUse = mutableStateOf(false)
    val isValidEmail = mutableStateOf(true)
    val isValidPassword = mutableStateOf(true)

    fun registerUser(user: User){
        viewModelScope.launch {
            validateData(user)
            if(isValidEmail.value)
            {
                isLoading.value = true
                val result = repository.signUpUser(user)
                when(result){
                    is Resource.Success -> {
                        Log.d("Response", "Success")
                        if(result.data?.returnCode == REGISTER_SUCCESSFULL ){
                            emailInUse.value = false
                            registeredSuccessfully.value = true
                        }
                    }
                    else -> {
                        if(result.data?.returnCode == EMAIL_IS_IN_USE ){
                            emailInUse.value = true
                            Log.d("Response", "Email is in use!")
                        }
                        Log.d("Response", "Error while registering")
                    }
                }
                isLoading.value = false
            }
        }
    }

    fun validateEmail(email: String){
        isValidEmail.value = email.matches(EmailRegex)
    }

    fun validatePassword(password: String){
        isValidPassword.value = password.length > 6
    }

    fun validateData(user: User){
        validateEmail(user.email)
        validatePassword(user.password)
    }
}
