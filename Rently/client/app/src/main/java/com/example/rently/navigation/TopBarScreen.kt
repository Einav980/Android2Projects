package com.example.rently.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class TopBarScreen(
    val route: String, val title: String, val icon: ImageVector
) {
    object Apartments: TopBarScreen(
        route = "apartments_screen",
        title = "Apartments",
        icon = Icons.Default.Home
    )

    object Profile: TopBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Default.AccountBox
    )

    object Settings: TopBarScreen(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )

    object Logout: TopBarScreen(
        route = "logout",
        title = "Logout",
        icon = Icons.Default.ExitToApp
    )

    object ManageApartmentTypes: TopBarScreen(
        route = "manage_apartment_type_screen",
        title = "Manage Apartment Type",
        icon = Icons.Default.Settings
    )
}