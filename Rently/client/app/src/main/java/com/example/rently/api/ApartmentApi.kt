package com.example.rently.api

import com.example.rently.model.Apartment
import com.example.rently.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApartmentApi {

    // Apartments
    @GET("/api/apartments")
    suspend fun listApartments(): ArrayList<Apartment>

    @GET("/api/apartments/624ca60f5c4af535859c0729")
    suspend fun getApartment(): Apartment
    // Users

}