package com.example.rently.api

import com.example.rently.model.google.GoogleLocationResponse
import com.example.rently.model.google.GooglePredictionsResponse
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

    @GET("maps/api/geocode/json")
    suspend fun getAddressLocation(
        @Query("key") key: String = Constants.GOOGLE_API_KEY,
        @Query("address") address: String
    ): GoogleLocationResponse

    companion object{
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}