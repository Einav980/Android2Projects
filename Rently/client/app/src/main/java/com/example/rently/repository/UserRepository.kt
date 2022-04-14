package com.example.rently.repository

import com.example.rently.Resource
import com.example.rently.api.UserApi
import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class UserRepository @Inject constructor(
    private val api: UserApi
) {
    suspend fun loginUser(user: User): Resource<AuthResponse>{
        val response = try {
            api.loginUser(user = user)
        } catch (e: Exception){
            return Resource.Error("Failed logging in")
        }

        return Resource.Success(response)
    }

    suspend fun signUpUser(user: User): Resource<AuthResponse>{
        val response = try{
            api.registerUser(user = user)
        } catch (e: Exception){
            return Resource.Error("Failed signing up")
        }
        return Resource.Success(response)
    }

    suspend fun listUsers(): ArrayList<User>{
        return api.listUsers()
    }

    suspend fun getUser(id: String): User{
        return api.getUser(id)
    }

}

