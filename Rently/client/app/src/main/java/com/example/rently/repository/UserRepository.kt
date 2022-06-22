package com.example.rently.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.rently.Resource
import com.example.rently.api.UserApi
import com.example.rently.model.Apartment
import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(
    private val api: UserApi,
    private val context: Context
) {
    suspend fun loginUser(user: User): Resource<AuthResponse>{
        return try {
            val response = api.loginUser(user = user)
            Resource.Success(response)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            Resource.Error("Failed logging in", AuthResponse(returnCode = 500, message = "Server error has occurred", type = "Error"))
        }
    }

      suspend fun signUpUser(user: User): Resource<AuthResponse>{
        val response = try{
            api.registerUser(user = user)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            return Resource.Error("Failed signing up", AuthResponse(returnCode = 500, message = "Server error has occurred", type = "Error"))
        }
        return Resource.Success(response)
    }

    suspend fun listUsers(): ArrayList<User>{
        return api.listUsers()
    }

    suspend fun getUser(email: String): Resource<User>{
        val response = try{
            api.getUser(email)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            return Resource.Error("User was not found!")
        }
        return Resource.Success(data = response)
    }

    suspend fun editUser(email: String, user: User): Resource<AuthResponse>{
        val response = try{
            api.editUser(email = email, user = user)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            return Resource.Error("Failed editing User", AuthResponse(returnCode = 500, message = "Server error has occurred", type = "Error"))
        }
        return Resource.Success(response)
    }
}

