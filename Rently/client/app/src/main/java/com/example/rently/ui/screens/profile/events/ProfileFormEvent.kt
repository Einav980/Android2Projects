package com.example.rently.ui.screens.profile.events

import com.example.rently.ui.screens.register.events.RegisterFormEvent

sealed class ProfileFormEvent{
    object Logout: ProfileFormEvent()
    object EditProfile: ProfileFormEvent()
    data class FirstNameChanged(val firstName: String): ProfileFormEvent()
    data class LastNameChanged(val lastName: String): ProfileFormEvent()
    data class PhoneChanged(val phone: String): ProfileFormEvent()
}