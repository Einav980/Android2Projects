package com.example.rently.validation.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidatePassword @Inject constructor(){
    fun execute(password: String): ValidateResult {
        if (password.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Password cannot be empty"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}