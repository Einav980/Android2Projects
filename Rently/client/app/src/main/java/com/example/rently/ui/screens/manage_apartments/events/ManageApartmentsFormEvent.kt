package com.example.rently.ui.screens.manage_apartments.events

import com.example.rently.model.Apartment

sealed class ManageApartmentsFormEvent{
    data class RemoveApartment(val apartment: Apartment): ManageApartmentsFormEvent()
    data class ApartmentStatusChanged(val apartment: Apartment): ManageApartmentsFormEvent()
}