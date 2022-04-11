package com.example.rently.model

data class AuthResponse(
    val returnCode: Int,
    val message: String,
    val type: String
) {
}