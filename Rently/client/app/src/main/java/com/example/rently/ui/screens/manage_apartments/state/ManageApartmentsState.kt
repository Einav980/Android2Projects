package com.example.rently.ui.screens.manage_apartments.state

import com.example.rently.model.Apartment

data class ManageApartmentsState(
   val screenLoading: Boolean = false,
   val apartments: ArrayList<Apartment> = ArrayList()
)
