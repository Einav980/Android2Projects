package com.example.rently.ui.screens.profile.state

data class ProfileState(
    val userApartments: Int = 0,
    val userWatchlist: Int = 0,
    val loading: Boolean = false,
    val userEmail: String = "",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val editableText: Boolean = false,
    val headLastname : String = "",
    val headFirstname : String = "",
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val phoneError: String? = null,
)
