package com.example.rently.ui.screens.admin_screens.manage_types

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.ApartmentType
import com.example.rently.repository.ApartmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageApartmentTypeViewModel @Inject constructor(
    private val repository: ApartmentRepository,
) : ViewModel() {

    val apartmentTypes = mutableStateOf(ArrayList<ApartmentType>())
    val errorHasOccured = mutableStateOf(false)
    val isLoading = mutableStateOf(true)

    fun listApartmentTypes(){
        isLoading.value = true
        viewModelScope.launch {
            val response = repository.listApartmentTypes()
            when(response) {
                is Resource.Success -> {
                    apartmentTypes.value = response.data!!
                }

                else -> {
                    errorHasOccured.value = true
                }
            }
            isLoading.value = false
        }
    }

    fun deleteApartmentType(apartmentTypeId: String){
        viewModelScope.launch {
            val response = repository.deleteApartmentType(apartmentTypeId)
            when(response){
                is Resource.Success -> {
                    listApartmentTypes()
                }
                else -> {
                    Log.d("Rently", "Error while deleting")
                }
            }
        }
    }

    fun addApartmentType(apartmentType: String){
        viewModelScope.launch {
            val response = repository.addApartmentType(apartmentType)
            when(response){
                is Resource.Success -> {
                    listApartmentTypes()
                }
                else -> {
                    Log.d("Rently", "Error while adding")
                }
            }
        }
    }

}