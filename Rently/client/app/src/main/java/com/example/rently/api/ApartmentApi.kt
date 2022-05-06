package com.example.rently.api

import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.User
import retrofit2.http.*

interface ApartmentApi {

    // List all apartments
    @GET("apartments")
    suspend fun listApartments(): ArrayList<Apartment>

    // Get specific apartment info
    @GET("apartments/624ca60f5c4af535859c0729")
    suspend fun getApartment(): Apartment

    // List all user apartments
    @GET("apartments/user/{userid}")
    suspend fun listUserApartments(@Path("userid") id: String): ArrayList<Apartment>

    @GET("apartments/types")
    suspend fun listApartmentTypes(): ArrayList<ApartmentType>

    @POST("apartments/types")
    suspend fun addApartmentType(@Body type: String): ApartmentType

    @DELETE("apartments/types/{typeId}")
    suspend fun deleteApartmentType(@Path("typeId") apartmentTypeId: String): ApartmentType
}