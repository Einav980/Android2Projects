package com.example.rently.validation.use_case

import javax.inject.Inject

class ValidateDescription @Inject constructor() {
    fun execute(description: String): ValidateResult {
        if (description.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Description cannot be empty"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}