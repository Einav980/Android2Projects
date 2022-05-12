package com.example.rently.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.rently.Resource
import com.example.rently.api.UserApi
import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.first
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@ActivityScoped
class UserRepository @Inject constructor(
    private val api: UserApi,
    private val context: Context
) {

    suspend fun loginUser(user: User): Resource<AuthResponse>{
        try {
            val response = api.loginUser(user = user)
            val datastoreKey = booleanPreferencesKey("isLoggedIn")
            context.datastore.edit { settings ->
                settings[datastoreKey] = true
            }
            return Resource.Success(response)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            return Resource.Error("Failed logging in", AuthResponse(returnCode = 500, message = "Server error has occurred", type = "Error"))
        }
    }

    suspend fun logoutUser(): Resource<Boolean> {
        try {
            val datastoreKey = booleanPreferencesKey("isLoggedIn")
            context.datastore.edit { settings ->
                settings[datastoreKey] = false
            }
            return Resource.Success(true)
        } catch (e: java.lang.Exception){
            return Resource.Error("Failed to change the login state of the user", false)
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

    suspend fun checkIfUserIsLoggedIn(): Resource<Boolean?>{
        try{
            val datastoreKey = booleanPreferencesKey("isLoggedIn")
            val preferences = context.datastore.data.first()
            return Resource.Success(preferences[datastoreKey])
        }
        catch (e: java.lang.Exception){
            return Resource.Error("Failed retrieving user state", false)
        }
    }

    suspend fun getUser(id: String): Resource<User>{
        val response = try{
            api.getUser(id)
        } catch (e: Exception){
            Timber.d("Response", e.message.toString())
            return Resource.Error("User was not found!")
        }
        return Resource.Success(data = response)
    }

}

