package com.example.rently.ui.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val EMAIL_IS_IN_USE = 405
    private val REGISTER_SUCCESSFULL = 201
    private val emailRegex =  Regex("^\\S+@\\S+\\.\\S+\$")
    private val phoneRegex =  Regex("^05\\d-[0-9]{7}")

    val isLoading = mutableStateOf(false)
    val registeredSuccessfully = mutableStateOf(false)
    val emailIsInUse = mutableStateOf(false)
    val isEmailValid = mutableStateOf(true)
    val isPasswordValid = mutableStateOf(true)
    val errorOccurred = mutableStateOf(false)
    val isPhoneValid = mutableStateOf(true)

    fun registerUser(user: User){
        viewModelScope.launch {
            Timber.d("Register")
            validateData(user)
            if(isEmailValid.value)
            {
                isLoading.value = true
                val result = repository.signUpUser(user)
                when(result){
                    is Resource.Success -> {
                        val returnCode = result.data?.returnCode
                        if( returnCode == REGISTER_SUCCESSFULL ){
                            registeredSuccessfully.value = true
                        }
                        else if(returnCode == EMAIL_IS_IN_USE){
                            emailIsInUse.value = true
                        }
                    }
                    else -> {
                        errorOccurred.value = true
                        Timber.d("Error while registering")
                    }
                }
                isLoading.value = false
            }
        }
    }

    private fun validateEmail(email: String){
        isEmailValid.value = email.matches(emailRegex)
    }

    private fun validatePassword(password: String){
        isPasswordValid.value = password.length > 6
    }

    private fun validatePhone(phone: String){
        isPhoneValid.value = phone.matches(phoneRegex)
    }

    private fun validateData(user: User){
        validateEmail(user.email)
        validatePassword(user.password)
        validatePhone(user.phone)
    }

    fun clearPasswordError(){
        isPasswordValid.value = true
    }

    fun clearEmailError(){
        isEmailValid.value = true
        emailIsInUse.value = false
    }

    fun clearPhoneError(){
        isPhoneValid.value = true
    }
}
