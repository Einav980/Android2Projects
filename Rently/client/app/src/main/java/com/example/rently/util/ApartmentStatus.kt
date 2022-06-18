package com.example.rently.util

import androidx.compose.ui.graphics.Color
import com.example.rently.ui.theme.ApartmentAvailableStatusColor
import com.example.rently.ui.theme.ApartmentClosedStatusColor
import com.example.rently.ui.theme.ApartmentPendingStatusColor
import com.example.rently.ui.theme.ApartmentRejectedStatusColor
import kotlinx.serialization.Serializable

enum class ApartmentStatus( val status: String, val backgroundColor: Color, val color: Color) {
    Available(status = "Available", backgroundColor = ApartmentAvailableStatusColor, color = Color.White),
    Closed(status = "Closed", backgroundColor = ApartmentClosedStatusColor, color = Color.White),
    Pending(status = "Pending", backgroundColor = ApartmentPendingStatusColor, color = Color.White),
    Rejected(status = "Rejected", backgroundColor = ApartmentRejectedStatusColor, color = Color.White),
}