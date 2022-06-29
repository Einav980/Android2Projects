package com.example.rently.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val RentlyPrimaryColor = Color(0xFFE91E5E)
val RentlyPrimaryVariantColor = Color(0xFFB41648)
val RentlySecondaryColor = Color(0xFF9ED0E6)
val RentlySecondaryVariantColor = Color(0xFF267192)
val RentlySubtitleTextColor = Color(0xFF777777)
val RentlyApartmentAddressColor = Color(0xFF0C121D)
val RentlyDrawerItemBackground = Color(0xF0FFC9D8)
val RentlyBackgroundColor = Color(0xFFFFFFFF)
val RentlyMapButtonBackgroundColor = Color(0xFFFFC107)
val RentlyMapButtonForegroundColor = Color(0xFF060830)
val RentlyStarColor = Color(0xFFFFC107)
val RentlyGrayColor = Color.Gray
val RentlyChipColor = Color(0xFFAABEC5)
val RentlyChipTextColor = Color(0xFF363B3D)
val RentlyLightColors = lightColors(
    background = RentlyBackgroundColor,
    primary = RentlyPrimaryColor,
    primaryVariant = RentlyPrimaryVariantColor,
    secondary = RentlySecondaryColor,
    secondaryVariant = RentlySecondaryVariantColor,
)

val RentlyDarkColors = lightColors(
    primary = Color(0xFF0B1931),
    surface = Color(0xFF121212),
    background = Color(0xFF1F1B24),
    primaryVariant = RentlyPrimaryVariantColor,
    secondary = RentlySecondaryColor,
    secondaryVariant = RentlySecondaryVariantColor,
)

val RentlyApartmentCardLightColors = lightColors(
    background = RentlyDrawerItemBackground,
    primary = RentlyPrimaryColor,
    primaryVariant = RentlyPrimaryVariantColor,
    secondary = RentlySecondaryColor,
    secondaryVariant = RentlyApartmentAddressColor,
)

val ApartmentClosedStatusColor = Color(0xFF000022)
val ApartmentRejectedStatusColor = Color(0xFFD1103A)
val ApartmentAvailableStatusColor = Color(0xFF02DE70)
val ApartmentPendingStatusColor = Color(0xFF778DA9)