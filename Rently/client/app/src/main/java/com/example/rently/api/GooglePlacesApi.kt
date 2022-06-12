package com.example.rently.api

import com.example.rently.model.GooglePrediction
import com.example.rently.model.GooglePredictionsResponse
import com.example.rently.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApi {
    @GET("maps/api/place/autocomplete/json")
    suspend fun getPredictions(
        @Query("key") key: String = Constants.GOOGLE_API_KEY,
        @Query("types") types: String = "address",
        @Query("input") input: String
    ): GooglePredictionsResponse

    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}