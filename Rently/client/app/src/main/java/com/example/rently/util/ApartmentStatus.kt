package com.example.rently.util

import androidx.compose.ui.graphics.Color
import com.example.rently.ui.theme.*
import kotlinx.serialization.Serializable

enum class ApartmentStatus( val status: String, val backgroundColor: Color, val color: Color) {
    Available(status = "Available", backgroundColor = RentlyPrimaryColor, color = Color.White),
    Closed(status = "Closed", backgroundColor = Color.White, color = RentlyPrimaryColor),
    Pending(status = "Pending", backgroundColor = Color.White, color = RentlyPrimaryColor),
    Rejected(status = "Rejected", backgroundColor = Color.White, color = RentlyPrimaryColor),
}