package com.example.rently.ui.screens.login.state

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val loginErrorMessage: String? = null
)