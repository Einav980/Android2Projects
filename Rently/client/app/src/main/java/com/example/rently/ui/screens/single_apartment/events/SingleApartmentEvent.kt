package com.example.rently.ui.screens.single_apartment.events

import com.example.rently.model.Apartment

sealed class SingleApartmentEvent{
    data class ApartmentChanged(val apartment: Apartment): SingleApartmentEvent()
}
