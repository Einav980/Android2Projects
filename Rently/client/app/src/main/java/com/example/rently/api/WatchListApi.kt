package com.example.rently.api

import com.example.rently.model.*
import com.example.rently.util.ApartmentStatus
import retrofit2.http.*

interface WatchListApi {

    // List all user watchlist apartments
    @GET("watchlists/user/{userid}")
    suspend fun listUserWatchListApartments(@Path("userid") id: String): ArrayList<Apartment>

    // remove apartment from watchlist for user
    @DELETE("watchlists/remove")
    suspend fun removeWatchItem(@Body watchList: WatchList): AuthResponse

    // add apartment to watchlist for user
    @POST("watchlists/add")
    suspend fun addWatchItem(@Body watchList: WatchList): AuthResponse
}