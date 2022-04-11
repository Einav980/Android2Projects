package com.example.rently.model

data class Apartment(
    val city: String,
    val price: Number,
){
    override fun toString(): String {
        return "city: $city, price: $price"
    }
}
