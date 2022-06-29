package com.example.rently.validation.use_case

import javax.inject.Inject

class ValidateFirstName @Inject constructor(){
    fun execute(firstName: String): ValidateResult {
        if (firstName.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Firstname cannot be empty"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}