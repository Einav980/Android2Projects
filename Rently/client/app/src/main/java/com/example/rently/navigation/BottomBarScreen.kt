package com.example.rently.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String, val title: String, val icon: ImageVector
    ) {
    object Apartments: BottomBarScreen(
        route = "apartments_screen",
        title = "Apartments",
        icon = Icons.Default.Home
    )

    object Profile: BottomBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Default.AccountBox
    )

    object Settings: BottomBarScreen(
        route = "settings_screen",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}