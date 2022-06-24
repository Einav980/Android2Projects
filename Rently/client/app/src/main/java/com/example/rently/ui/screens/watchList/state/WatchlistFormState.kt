package com.example.rently.ui.screens.watchList.state

import com.example.rently.model.Apartment

data class WatchlistState(
    val apartments: List<Apartment> = emptyList(),
    val loading: Boolean = false,
    val userEmail: String = ""
)
