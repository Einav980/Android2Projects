package com.example.rently.validation.use_case

import android.graphics.Bitmap
import com.example.rently.model.ApartmentType
import com.example.rently.model.google.GoogleLocation

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
    var isFurnished: Boolean = false,
    val hasBalcony: Boolean = false,
    val showPredictions: Boolean = false,
    val city: String = "",
    val placesLoading: Boolean = false,
    val uploadedImageUrl: String = "",
    val numberOfBedrooms: Int = 0,
    val numberOfBathrooms: Int = 0,
    val apartmentImageUrl: String = "",
    val apartmentTypes: List<ApartmentType> = emptyList(),
    val isApartmentTypesLoading: Boolean = false,
    val selectedApartmentTypeIndex: Int = 0,
    val apartmentIsUploading: Boolean = false,
    val apartmentAddressLocation: GoogleLocation = GoogleLocation(0,0)
)
