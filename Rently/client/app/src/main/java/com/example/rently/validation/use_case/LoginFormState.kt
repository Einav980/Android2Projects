package com.example.rently.validation.use_case

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val loginErrorMessage: String? = null
)