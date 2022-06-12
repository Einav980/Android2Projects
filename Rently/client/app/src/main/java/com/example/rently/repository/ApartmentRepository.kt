package com.example.rently.repository

import android.util.Log
import com.example.rently.Resource
import com.example.rently.api.ApartmentApi
import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import dagger.hilt.android.scopes.ActivityScoped
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class ApartmentRepository @Inject constructor(
    private val api: ApartmentApi,
) {
    suspend fun listApartments(): Resource<ArrayList<Apartment>> {
        val response = try {
            api.listApartments()
        } catch (e: Exception) {
            return Resource.Error("Failed listing apartments")
        }

        return Resource.Success(response)
    }

    suspend fun listUserApartments(userId: String): Resource<ArrayList<Apartment>> {
        val response = try {
            api.listUserApartments(userId)
        } catch (e: Exception) {
            return Resource.Error("Failed listing user apartments")
        }

        return Resource.Success(response)
    }

    suspend fun listApartmentTypes(): Resource<ArrayList<ApartmentType>> {
        val response = try {
            api.listApartmentTypes()
        } catch (e: Exception) {
            return Resource.Error("Failed listing apartment types")
        }

        return Resource.Success(response)
    }

    suspend fun addApartmentType(type: String): Resource<ApartmentType> {
        val response = try {
            api.addApartmentType(type)
        } catch (e: Exception) {
            return Resource.Error("Failed adding apartment type")
        }
        return Resource.Success(response)
    }

    suspend fun deleteApartmentType(id: String): Resource<ApartmentType> {
        val response = try {
            api.deleteApartmentType(apartmentTypeId = id)
        } catch (e: Exception) {
            return Resource.Error("Failed deleting apartment type")
        }
        return Resource.Success(response)
    }

    suspend fun addApartment(apartment: Apartment): Resource<AuthResponse>{
        val response = try{
            api.addApartment(apartment = apartment)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            return Resource.Error("Failed adding Apartment", AuthResponse(returnCode = 500, message = "Server error has occurred", type = "Error"))
        }
        return Resource.Success(response)
    }
}