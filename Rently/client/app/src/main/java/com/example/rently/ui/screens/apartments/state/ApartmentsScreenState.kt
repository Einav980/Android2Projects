package com.example.rently.ui.screens.apartments.state

import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.Watchlist

data class ApartmentsScreenState(
    val apartments: List<Apartment> = emptyList(),
    val apartmentTypes: List<ApartmentType> = emptyList(),
    val apartmentTypesLoading: Boolean = false,
    val loading: Boolean = false,
    val isFiltered: Boolean = false,
    val userWatchlist: List<Watchlist> = emptyList()
)
