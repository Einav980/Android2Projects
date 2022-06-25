package com.example.rently.ui.screens.apartments.events

import com.example.rently.model.Apartment

sealed class ApartmentsFormEvent{
    data class AddToWatchlist(val apartmentId: String): ApartmentsFormEvent()
    data class RemoveFromWatchList(val apartmentId: String): ApartmentsFormEvent()
    object FilterApplied: ApartmentsFormEvent()
}
