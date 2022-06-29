package com.example.rently.model

import com.example.rently.util.UserType

data class User(
    val email: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val type: String = UserType.Normal.type,
    val password: String = "",
    val phone: String = ""
)
