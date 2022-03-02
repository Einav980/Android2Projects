package com.example.countries.models

import com.google.gson.annotations.SerializedName

data class Country(
    var nativeName: String,
    var name: String,
    var borders: ArrayList<String>,
    @SerializedName("flags")
    var flag: Flag
) {}