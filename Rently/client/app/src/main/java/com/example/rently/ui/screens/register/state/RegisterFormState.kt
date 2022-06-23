package com.example.rently.ui.screens.register.state

import android.graphics.Bitmap
import com.example.rently.model.ApartmentType
import com.example.rently.model.google.GoogleLocation

data class RegisterFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val phone: String = "",
    val phoneError: String? = null,
    val registerLoading: Boolean = false,
    val registerErrorMessage: String? = null,
)
