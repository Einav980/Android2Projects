package com.example.rently.ui.screens.filter

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.rently.validation.presentation.FilterFormEvent
import com.example.rently.validation.use_case.FilterFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(FilterFormState())


    fun onEvent(event: FilterFormEvent){
        when(event){
            is FilterFormEvent.CitiesChanged -> {
                state = state.copy(cities = event.cities)
            }

            is FilterFormEvent.PriceRangeChanged -> {
                state = state.copy(priceRange = event.priceRange)
            }

            is FilterFormEvent.SizeChanged -> {
                state = state.copy(size = event.size)
            }

            is FilterFormEvent.NumberOfBathroomsChanged -> {
                state = state.copy(numberOfBathrooms = event.amount)
            }

            is FilterFormEvent.PropertyTypesChanged -> { //todo fix the property type in the ui
                state = state.copy(propertyTypes = event.types)
            }

            is FilterFormEvent.NumberOfBedroomsChanged -> {
                state = state.copy(numberOfBedrooms = event.amount)
            }

            is FilterFormEvent.HasBalconyChanged -> {
                state = state.copy(hasBalcony = event.checkState)
            }

            is FilterFormEvent.HasParkingChanged -> {
                state = state.copy(hasParking = event.checkState)
            }

            is FilterFormEvent.IsAnimalFriendlyChanged -> {
                state = state.copy(isAnimalFriendly = event.checkState)
            }

            is FilterFormEvent.IsFurnishedChanged -> {
                state = state.copy(isFurnished = event.checkState)
            }

            is FilterFormEvent.Submit -> {
                filterData()
            }

        }
    }

    private fun filterData() {
    }
}