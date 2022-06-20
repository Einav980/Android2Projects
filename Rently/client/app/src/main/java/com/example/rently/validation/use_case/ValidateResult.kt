package com.example.rently.validation.use_case

data class ValidateResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
