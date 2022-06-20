package com.example.rently.validation.presentation

import com.example.rently.model.ApartmentType

sealed class FilterFormEvent{
    data class CitiesChanged(val cities: ArrayList<String>): FilterFormEvent()
    data class PriceRangeChanged(val priceRange: ClosedFloatingPointRange<Float>): FilterFormEvent()
    data class SizeChanged(val size: Float): FilterFormEvent()
    data class PropertyTypesChanged(val types: ArrayList<ApartmentType>): FilterFormEvent()
    data class NumberOfBedroomsChanged(val amount: Int): FilterFormEvent()
    data class NumberOfBathroomsChanged(val amount: Int): FilterFormEvent()
    data class IsAnimalFriendlyChanged(val checkState: Boolean): FilterFormEvent()
    data class IsFurnishedChanged(val checkState: Boolean): FilterFormEvent()
    data class HasBalconyChanged(val checkState: Boolean): FilterFormEvent()
    data class HasParkingChanged(val checkState: Boolean): FilterFormEvent()
    object Submit: FilterFormEvent()
}
