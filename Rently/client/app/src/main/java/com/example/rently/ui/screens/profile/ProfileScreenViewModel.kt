package com.example.rently.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.User
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.DatastorePreferenceRepository
import com.example.rently.repository.UserRepository
import com.example.rently.validation.presentation.ProfileFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val datastore: DatastorePreferenceRepository, private val userRepository: UserRepository, private val apartmentRepository: ApartmentRepository) :
    ViewModel() {

    private val phoneRegex =  Regex("^05\\d[0-9]{7}")


    val editableText= mutableStateOf(false)
    val email = mutableStateOf("")
    val lastname = mutableStateOf("")
    val myApartments = mutableStateOf(0)
    val firstname = mutableStateOf("")
    val headLastname = mutableStateOf("")
    val headFirstname = mutableStateOf("")
    val phone = mutableStateOf("")
    val user = mutableStateOf(User())
    val isPhoneValid = mutableStateOf(true)
    val isLastNameValid = mutableStateOf(true)
    val isFirstNameValid = mutableStateOf(true)

    fun onEvent(event: ProfileFormEvent){
        when(event){
            is ProfileFormEvent.Logout -> {
                logout()
            }
        }
    }

    fun isUserHeadNotEmpty(): Boolean{
        return headLastname.value.isNotEmpty() && headFirstname.value.isNotEmpty()
    }

    fun isUserInfoValid(): Boolean{
        return isPhoneValid.value && isLastNameValid.value && isFirstNameValid.value
    }

    fun getLoggedInUser() {
        viewModelScope.launch {
            val userEmailResult = datastore.getUserEmail().first()
            if (userEmailResult.isNotEmpty()){
                val response = userRepository.getUser(userEmailResult)
                when(response){
                    is Resource.Success -> {
                        val data =  response.data!!
                        email.value = data.email
                        lastname.value = data.lastname
                        firstname.value = data.firstname
                        phone.value = data.phone
                        headLastname.value = data.lastname
                        headFirstname.value = data.firstname
                    }
                    else -> {
                        Log.d("Rently", "could not find user")}
                }
            }
        }
    }

    fun getLoggedInUserApartments() {
        viewModelScope.launch {
            val userEmailResult = datastore.getUserEmail().first()
            if (userEmailResult.isNotEmpty()){
                val response = apartmentRepository.listUserApartments(userEmailResult)
                when(response){
                    is Resource.Success -> {
                        val data =  response.data!!
                        myApartments.value = data.size
                    }
                    else -> {
                        Log.d("Rently", "could not find user")}
                }
            }
        }
    }

    fun editTextFields() {
        if(editableText.value){
            viewModelScope.launch {
                val userEmailResult = datastore.getUserEmail().first()
                validateData()
                if (userEmailResult.isNotEmpty() && isUserInfoValid()){
                    val response = userRepository.editUser(userEmailResult, User(firstname = firstname.value, lastname = lastname.value, phone = phone.value))
                    when(response){
                        is Resource.Success -> {
                            editableText.value = false
                            updateHeadName()
                            Log.d("Rently", "Edit user successfully \n ${response}")
                        }
                        else -> {
                            Log.d("Rently", "could not edit user")}
                    }
                }
            }
        }else{
            editableText.value = true
        }
    }


    private fun validatePhone(phone: String){
        isPhoneValid.value = phone.matches(phoneRegex)
    }

    private fun validateLastName(lastName: String){
        isLastNameValid.value = lastName.isNotEmpty()
    }

    private fun validateFirstName(firstName: String){
        isFirstNameValid.value = firstName.isNotEmpty()
    }

    private fun validateData(){
        validatePhone(phone.value)
        validateLastName(lastname.value)
        validateFirstName(firstname.value)
    }

    fun clearPhoneError(){
        isPhoneValid.value = true
    }

    fun clearFirstNameError(){
        isFirstNameValid.value = true
    }

    fun clearLastNameError(){
        isLastNameValid.value = true
    }

    fun updateHeadName(){
        headLastname.value = lastname.value
        headFirstname.value = firstname.value
    }

    private fun logout(){
        viewModelScope.launch {
            datastore.setLoggedOut()
        }
    }
}
