package com.example.rently.validation.use_case

import javax.inject.Inject

class ValidateRegisterPassword @Inject constructor(){

    fun execute(password: String): ValidateResult {
        if (password.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Password cannot be empty"
            )
        }
        if(password.length  < 8){
            return ValidateResult(
                successful = false,
                errorMessage = "Password must contain at least 8 characters"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}