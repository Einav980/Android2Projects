package com.example.rently.model

import android.os.Parcelable
import com.example.rently.util.ApartmentStatus
import com.example.rently.util.ApartmentType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Apartment(
    val _id: String = "",
    val city: String,
    val numberOfRooms: Int,
    val numberOfBaths: Int,
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val numberOfBeds: Int,
    val address: String,
    val size: Int,
    val type: ApartmentType = ApartmentType.Regular,
    val imageUrl: String,
    val price: Int,
    val floor: Int = 1,
    val apartmentNumber: Int = 2,
    val status: ApartmentStatus = ApartmentStatus.PENDING,
    val userId: String = "einav980@gmail.com"
) : Parcelable {
//    override fun toString(): String {
//        return "city: $city, price: $price"
//    }
}
