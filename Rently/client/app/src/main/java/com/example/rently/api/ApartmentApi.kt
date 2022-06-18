package com.example.rently.api

import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import com.example.rently.util.ApartmentStatus
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

    @POST("apartments/types/{typeName}")
    suspend fun addApartmentType(@Path("typeName") type: String): ApartmentType

    @DELETE("apartments/types/{typeId}")
    suspend fun deleteApartmentType(@Path("typeId") apartmentTypeId: String): ApartmentType

    @DELETE("apartments/{id}")
    suspend fun deleteApartment(@Path("id") apartmentId: String): Apartment

    @PUT("apartments/{id}/status")
    suspend fun editApartmentStatus(@Path("id") apartmentId: String, @Body status: String): AuthResponse

    @POST("apartments/add")
    suspend fun addApartment(@Body apartment: Apartment): AuthResponse

    @PUT("apartments/{id}")
    suspend fun editApartment(@Path("id") apartmentId: String, @Body apartment: Apartment): AuthResponse
}