package com.example.rently.repository

import com.example.rently.Resource
import com.example.rently.api.ApartmentApi
import com.example.rently.model.Apartment
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApartmentRepository @Inject constructor(
    private val api: ApartmentApi
){
    suspend fun listApartments(): Resource<ArrayList<Apartment>> {
        val response = try {
            api.listApartments()
        } catch (e: Exception){
            return Resource.Error("Failed logging in")
        }

        return Resource.Success(response)
    }
}