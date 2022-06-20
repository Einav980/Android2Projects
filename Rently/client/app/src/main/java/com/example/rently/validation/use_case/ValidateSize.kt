package com.example.rently.validation.use_case

import javax.inject.Inject

class ValidateSize @Inject constructor(){
    fun execute(size: Int): ValidateResult {
        if (size <= 0) {
            return ValidateResult(
                successful = false,
                errorMessage = "Size must be greater than 0"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}