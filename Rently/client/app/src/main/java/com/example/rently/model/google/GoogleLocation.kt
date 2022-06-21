package com.example.rently.model.google

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoogleLocation(
    val lat: Number,
    val lng: Number
): Parcelable
