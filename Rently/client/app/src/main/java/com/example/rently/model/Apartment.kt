package com.example.rently.model

import android.os.Parcelable
import com.example.rently.model.google.GoogleLocation
import com.example.rently.model.google.GoogleLocationResponse
import com.example.rently.util.ApartmentStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apartment(
    val _id: String = "",
    val city: String = "",
    val description: String = "",
    val numberOfBaths: Int = 0,
    val numberOfBeds: Int = 0,
    val location: GoogleLocation = GoogleLocation(0, 0),
    val address: String = "",
    val size: Int = 0,
    val type: String = "",
    val imageUrl: String = "https://www.avirealty.com/assets/images/house-placeholder.jpg",
    val price: Int = 0,
    var status: String = ApartmentStatus.Pending.status,
    val userId: String = "",
    var hasBalcony: Boolean = false,
    var hasParking: Boolean = false,
    var isAnimalFriendly: Boolean = false,
    var isFurnished: Boolean = false
) : Parcelable
