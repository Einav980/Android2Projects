package com.example.rently.validation.use_case

import javax.inject.Inject

class ValidateAddress @Inject constructor(){
    fun execute(address: String): ValidateResult {
        if (address.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Address cannot be empty"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}