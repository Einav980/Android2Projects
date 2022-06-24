package com.example.rently.api

import com.example.rently.model.Apartment
import com.example.rently.model.AuthResponse
import com.example.rently.model.Watchlist
import retrofit2.http.*

interface WatchListApi {

    // List all user watchlist apartments
    @GET("watchlist/user/{userid}")
    suspend fun listUserWatchListApartments(@Path("userid") id: String): ArrayList<Apartment>

    // remove apartment from watchlist for user
    @POST("watchlist/remove")
    suspend fun removeWatchItem(@Body watchList: Watchlist): AuthResponse

    // add apartment to watchlist for user
    @POST("watchlist/add")
    suspend fun addWatchItem(@Body watchList: Watchlist): AuthResponse
}