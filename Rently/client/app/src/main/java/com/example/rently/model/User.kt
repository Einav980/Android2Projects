package com.example.rently.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    val email: String,
    val password: String,
    val phone: String = ""
)
