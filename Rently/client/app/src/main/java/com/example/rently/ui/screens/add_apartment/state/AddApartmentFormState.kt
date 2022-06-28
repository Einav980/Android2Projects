package com.example.rently.ui.screens.add_apartment.state

import android.graphics.Bitmap
import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.google.GoogleLocation
import com.google.android.gms.maps.model.LatLng

data class AddApartmentFormState(
    val apartmentAddress: String = "",
    val addressError: String? = null,
    val apartmentDescription: String = "",
    val descriptionError: String? = null,
    val apartmentType: String = "",
    val apartmentPrice: Int = 0,
    val priceError: String? = null,
    val apartmentSize: Int = 0,
    val sizeError: String? = null,
    val image: Bitmap? = null,
    val hasParking: Boolean = false,
    val isAnimalFriendly: Boolean = false,
    var isFurnished: Boolean = false,
    val hasBalcony: Boolean = false,
    val showPredictions: Boolean = false,
    val apartmentCity: String = "",
    val placesLoading: Boolean = false,
    val numberOfBedrooms: Int = 0,
    val numberOfBathrooms: Int = 0,
    val apartmentImageUrl: String = "https://www.avirealty.com/assets/images/house-placeholder.jpg",
    val apartmentTypes: List<ApartmentType> = emptyList(),
    val isApartmentTypesLoading: Boolean = false,
    val selectedApartmentTypeIndex: Int = 0,
    val apartmentIsUploading: Boolean = false,
    val apartmentAddressLocation: LatLng = LatLng(0.0, 0.0),
    val apartmentImageBitmap: Bitmap? = null
)
