package com.example.rently.validation.use_case

import javax.inject.Inject

class ValidatePrice @Inject constructor(){
    fun execute(price: Int): ValidateResult {
        if (price <= 0) {
            return ValidateResult(
                successful = false,
                errorMessage = "Price must be greater than 0"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}