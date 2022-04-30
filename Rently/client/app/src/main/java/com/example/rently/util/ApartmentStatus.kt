package com.example.rently.util

import androidx.compose.ui.graphics.Color
import com.example.rently.ui.theme.ApartmentAvailableStatusColor
import com.example.rently.ui.theme.ApartmentClosedStatusColor
import com.example.rently.ui.theme.ApartmentPendingStatusColor
import com.example.rently.ui.theme.ApartmentRejectedStatusColor

enum class ApartmentStatus(val status: String, val backgroundColor: Color, val color: Color) {
    AVAILABLE(status = "Available", backgroundColor = ApartmentAvailableStatusColor, color = Color.White),
    CLOSED(status = "Closed", backgroundColor = ApartmentClosedStatusColor, color = Color.White),
    PENDING(status = "Pending", backgroundColor = ApartmentPendingStatusColor, color = Color.White),
    REJECTED(status = "Rejected", backgroundColor = ApartmentRejectedStatusColor, color = Color.White),
}