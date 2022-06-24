package com.example.rently.api

import com.example.rently.model.AuthResponse
import com.example.rently.model.User
import retrofit2.http.*

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

    @PUT("/api/users/{email}")
    suspend fun editUser(@Path("email") email: String, @Body user: User): AuthResponse

}