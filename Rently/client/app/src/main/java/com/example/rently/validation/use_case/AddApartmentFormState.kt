package com.example.rently.validation.use_case

import android.graphics.Bitmap

data class AddApartmentFormState(
    val address: String = "",
    val addressError: String? = null,
    val description: String = "",
    val descriptionError: String? = null,
    val price: Int = 0,
    val priceError: String? = null,
    val size: Int = 0,
    val sizeError: String? = null,
    val image: Bitmap? = null,
    val hasParking: Boolean = false,
    val isAnimalFriendly: Boolean = false,
    val isFurnished: Boolean = false,
    val hasBalcony: Boolean = false,
    val showPredictions: Boolean = false,
    val city: String = "",
    val placesLoading: Boolean = false,
    val uploadedImageUrl: String = ""
)
