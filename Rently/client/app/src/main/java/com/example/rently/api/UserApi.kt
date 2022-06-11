package com.example.rently.api

import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi{
    @POST("/api/users/signup")
    suspend fun registerUser(
        @Body user: User
    ): AuthResponse

    @POST("/api/users/login")
    suspend fun loginUser(
        @Body user: User
    ): AuthResponse

    @GET("/api/users")
    suspend fun listUsers(): ArrayList<User>

    @GET("/api/users/{email}")
    suspend fun getUser(@Path("email") email: String): User

}