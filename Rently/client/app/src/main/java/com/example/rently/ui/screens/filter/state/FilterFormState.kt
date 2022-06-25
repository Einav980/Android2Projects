package com.example.rently.ui.screens.filter.state

import com.example.rently.model.ApartmentType

data class FilterFormState(
    val cities: ArrayList<String> = ArrayList(),
    val priceRange: ClosedFloatingPointRange<Float> = 0.25f..0.75f,
    val size: Float = 0.5f,
    val propertyTypes: ArrayList<ApartmentType> = ArrayList(),
    val numberOfBedrooms: Int = 1,
    val numberOfBathrooms: Int = 1,
    val hasParking: Boolean = false,
    val isAnimalFriendly: Boolean = false,
    val isFurnished: Boolean = false,
    val hasBalcony: Boolean = false,
)
