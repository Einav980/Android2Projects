package com.example.rently.validation.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidatePhone @Inject constructor(){
    fun execute(phone: String): ValidateResult {
        if (phone.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Phone cannot be empty"
            )
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            return ValidateResult(
                successful = false,
                errorMessage = "Phone is invalid"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}