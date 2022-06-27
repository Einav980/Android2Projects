package com.example.rently.ui.screens.map.events

import com.example.rently.model.Apartment

sealed class MapEvent{
    data class SelectedApartmentChanged(val apartment: Apartment): MapEvent()
}
