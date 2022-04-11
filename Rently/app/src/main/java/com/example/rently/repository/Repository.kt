package com.example.rently.repository

import com.example.rently.api.ApartmentApiModule
import com.example.rently.model.Apartment

class Repository {
    suspend fun listApartments(): ArrayList<Apartment>{
        return ApartmentApiModule.api.listApartments()
    }

    suspend fun getApartment(): Apartment{
        return ApartmentApiModule.api.getApartment()
    }
}