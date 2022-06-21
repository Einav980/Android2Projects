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
    val imageUrl: String = "",
    val price: Int = 0,
    val floor: Int = 1,
    val apartmentNumber: Int = 2,
    var status: String = ApartmentStatus.Pending.status,
    val userId: String = "einav980@gmail.com",
    var hasBalcony: Boolean = false,
    var hasParking: Boolean = false,
    var isAnimalsFriendly: Boolean = false,
    var isFurnished: Boolean = false
) : Parcelable
