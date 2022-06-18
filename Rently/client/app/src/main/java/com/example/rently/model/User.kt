package com.example.rently.model

import androidx.compose.runtime.MutableState
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    val email: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val type: String = "",
    val password: String = "",
    val phone: String = ""
)
