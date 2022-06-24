package com.example.rently.ui.screens.apartments

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.util.toRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.repository.ApartmentRepository
import com.example.rently.util.ApartmentStatus
import com.example.rently.validation.use_case.FilterFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ApartmentsViewModel @Inject constructor(private val repository: ApartmentRepository): ViewModel(){
    val apartments = mutableStateListOf<Apartment>()
    val isLoading = mutableStateOf(false)


    fun listApartments(state: FilterFormState?) {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.listApartments()
            when(response){
                is Resource.Success -> {
                    if (state != null) {
                        apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status &&
                                apartment.numberOfBaths >= state.numberOfBathrooms + 1 &&
                                apartment.numberOfBeds >= state.numberOfBedrooms + 1 &&
                                apartment.price in (state.priceRange.toRange().lower * 10000).roundToInt()..
                                (state.priceRange.toRange().upper * 10000).roundToInt() &&
                                apartment.size >= (state.size * 150).roundToInt() &&
                                isCityMatch(state.cities, apartment.city)

                            //todo add filters for properties and apartment type
                        })
                    }else{
                        apartments.addAll(response.data!!.filter { apartment -> apartment.status == ApartmentStatus.Available.status})
                    }
                }
            }
            isLoading.value = false
        }
    }

    private fun isCityMatch(citiesList: ArrayList<String>, city: String): Boolean {
        if(citiesList.isNotEmpty()){
            return citiesList.contains(city)
        }
        return true
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