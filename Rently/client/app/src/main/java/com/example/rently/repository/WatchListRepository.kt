package com.example.rently.repository

import com.example.rently.Resource
import com.example.rently.api.WatchListApi
import com.example.rently.model.*
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WatchlistRepository @Inject constructor(
    private val api: WatchListApi,
) {

    suspend fun listUserWatchListApartments(userId: String): Resource<ArrayList<Apartment>> {
        val response = try {
            api.listUserWatchListApartments(id = userId)
        } catch (e: Exception) {
            return Resource.Error("Failed listing user watchlist apartments")
        }
        return Resource.Success(response)
    }

    suspend fun listUserWatchlistItems(userId: String): Resource<List<Watchlist>> {
        val response = try {
            api.listUserWatchlistItems(userId = userId)
        } catch (e: Exception) {
            return Resource.Error("Failed listing user watchlist apartments")
        }
        return Resource.Success(response)
    }


    suspend fun removeWatchListApartment(watchList: Watchlist): Resource<AuthResponse> {
        val response = try {
            api.removeWatchItem(watchList = watchList)
        } catch (e: Exception) {
            return Resource.Error("Failed removing apartment from watchlist")
        }
        return Resource.Success(response)
    }

    suspend fun addWatchListApartment(watchList: Watchlist): Resource<AuthResponse>{
        val response = try{
            api.addWatchItem(watchList = watchList)
        } catch (e: Exception){
            return Resource.Error("Failed adding Apartment to watchlist", AuthResponse(returnCode = 500, message = "Server error has occurred", type = "Error"))
        }
        return Resource.Success(response)
    }
}