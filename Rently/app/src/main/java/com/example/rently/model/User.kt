package com.example.rently.model

data class User(
    val email: String,
    val password: String,
    val phone: String = ""
)
