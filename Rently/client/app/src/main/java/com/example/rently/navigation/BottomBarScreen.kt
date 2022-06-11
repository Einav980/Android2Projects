package com.example.rently.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

    object Favorites: BottomBarScreen(
        route = "Favorites_screen",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )

    object ManageApartments: BottomBarScreen(
        route = "manage_apartments",
        title = "Apartments",
        icon = Icons.Default.Edit
    )
}