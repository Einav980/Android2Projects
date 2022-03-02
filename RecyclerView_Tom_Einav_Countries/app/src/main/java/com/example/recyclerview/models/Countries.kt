package com.example.recyclerview.models

data class Country(
    var id: Long,
    var nativeName: String,
    var englishName: String,
    var neighborCountries: ArrayList<String>,
) {}