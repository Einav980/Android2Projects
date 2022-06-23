package com.example.rently.ui.screens.register.events

sealed class RegisterFormEvent{
    data class EmailChanged(val email: String): RegisterFormEvent()
    data class PasswordChanged(val password: String): RegisterFormEvent()
    data class FirstNameChanged(val firstName: String): RegisterFormEvent()
    data class LastNameChanged(val lastName: String): RegisterFormEvent()
    data class PhoneChanged(val phone: String): RegisterFormEvent()
    object Register: RegisterFormEvent()
}
