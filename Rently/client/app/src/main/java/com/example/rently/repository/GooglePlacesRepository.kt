package com.example.rently.repository

import android.util.Log
import com.example.rently.Resource
import com.example.rently.api.GooglePlacesApi
import com.example.rently.model.google.GoogleLocationResponse
import com.example.rently.model.google.GooglePredictionsResponse
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class GooglePlacesRepository @Inject constructor(
    private val api: GooglePlacesApi,
){
    suspend fun getPredictions(input: String): Resource<GooglePredictionsResponse>{
        val response = try {
            api.getPredictions(input = input)
        } catch (e: Exception) {
            Log.d("Rently", "Exception: ${e}")
            return Resource.Error("Failed prediction")
        }

        return Resource.Success(response)
    }

    suspend fun getAddressLocation(address: String): Resource<GoogleLocationResponse>{
        val response = try {
            api.getAddressLocation(address = address)
        } catch (e: Exception) {
            Log.d("Rently", "Exception: ${e}")
            return Resource.Error("Failed getting address location")
        }

        return Resource.Success(response)
    }
}