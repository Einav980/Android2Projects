package com.example.rently.repository

import android.util.Log
import com.example.rently.Resource
import com.example.rently.api.GooglePlacesApi
import com.example.rently.model.GooglePrediction
import com.example.rently.model.GooglePredictionsResponse
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
}