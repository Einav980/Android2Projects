package com.example.rently.validation.use_case

import android.util.Patterns
import javax.inject.Inject

class ValidatePassword @Inject constructor(){

    companion object{
        val PASSWORD_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*d)[a-zA-Zd]{8,}$")
    }

    fun execute(password: String): ValidateResult {
        if (password.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Password cannot be empty"
            )
        }
//        if(password.length  < 8){
//            return ValidateResult(
//                successful = false,
//                errorMessage = "Password must contain at least 8 characters"
//            )
//        }
//        if(!password.matches(PASSWORD_REGEX)){
//            return ValidateResult(
//                successful = false,
//                errorMessage = "Password must contain minimum 8 characters: at least 1 uppercase, 1 lowercase and 1 digit"
//            )
//        }
        return ValidateResult(
            successful = true
        )
    }
}