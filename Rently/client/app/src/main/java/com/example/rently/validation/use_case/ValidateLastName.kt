package com.example.rently.validation.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidateLastName @Inject constructor(){
    fun execute(lastname: String): ValidateResult {
        if (lastname.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Lastname cannot be empty"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}