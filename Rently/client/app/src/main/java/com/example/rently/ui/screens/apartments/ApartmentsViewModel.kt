package com.example.rently.ui.screens.apartments

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.repository.ApartmentRepository
import com.example.rently.validation.use_case.ApartmentsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApartmentsViewModel @Inject constructor(private val repository: ApartmentRepository) :
    ViewModel() {
/*
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)
*/

    var state by mutableStateOf(ApartmentsScreenState())


    init {
        fetchApartments()
    }
//    fun listApartments(state: FilterFormState?) {
//        viewModelScope.launch {
//            isLoading.value = true
//            val response = repository.listApartments()
//            when(response){
//                is Resource.Success -> {
//                    if (state != null) {
//                        apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status &&
//                                apartment.numberOfBaths >= state.numberOfBathrooms + 1 &&
////                                apartment.numberOfRooms >= state.numberOfBedrooms + 1 &&
//                                apartment.price in (state.priceRange.toRange().lower * 10000).roundToInt()..
//                                (state.priceRange.toRange().upper * 10000).roundToInt() &&
//                                apartment.size >= (state.size * 150).roundToInt() &&
//                                isCityMatch(state.cities, apartment.city)
//
//                            //todo add filters for properties and apartment type
//                        })
//                    }else{
//                        apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status})
//                    }
//                }
//            }
//            isLoading.value = false
//        }
//    }

    private fun isCityMatch(citiesList: ArrayList<String>, city: String): Boolean {
        if (citiesList.isNotEmpty()) {
            return citiesList.contains(city)
        }
        return true
    }

    private fun fetchApartments() {
        viewModelScope.launch {
            state = state.copy(loading = true)
            val response = repository.listApartments()
            when (response) {
                is Resource.Success -> {
                    state = state.copy(apartments = response.data!!)
                }
            }
            state = state.copy(loading = false)
        }
    }


//    fun addApartment(apartment: Apartment) {
//        viewModelScope.launch {
//            val response = repository.addApartment(apartment = apartment)
//            when(response){
//                is Resource.Success -> {
//                    Timber.d("created successfully")
//                }
//            }
//        }
//    }

}