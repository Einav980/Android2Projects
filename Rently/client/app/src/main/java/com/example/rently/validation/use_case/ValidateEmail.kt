package com.example.rently.validation.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail @Inject constructor(){
    fun execute(email: String): ValidateResult {
        if (email.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Email cannot be empty"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Not a valid email address"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}