package com.example.rently.model

import com.example.rently.util.ApartmentStatus
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

data class Apartment(
    val city: String,
    val numberOfRooms: Number,
    val numberOfBaths: Number,
    val lat: Number = 0,
    val lng: Number = 0,
    val numberOfBeds: Number,
    val address: String,
    val size: Number,
    val type: String,
    val imageUrl: String,
    val price: Number,
//    val status: ApartmentStatus = ApartmentStatus.PENDING
){
//    override fun toString(): String {
//        return "city: $city, price: $price"
//    }
}
