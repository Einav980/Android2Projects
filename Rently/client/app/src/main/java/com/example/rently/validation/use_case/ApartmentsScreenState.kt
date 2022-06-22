package com.example.rently.validation.use_case

import com.example.rently.model.Apartment

data class ApartmentsScreenState(
    val apartments: List<Apartment> = emptyList(),
    val loading: Boolean = false,
)
