package com.example.rently.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String, val title: String, val icon: ImageVector
) {
    object Apartments: BottomBarScreen(
        route = "apartments_screen",
        title = "Apartments",
        icon = Icons.Filled.Home
    )

    object Profile: BottomBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Filled.Person
    )

    object Favorites: BottomBarScreen(
        route = "favorites_screen",
        title = "Favorites",
        icon = Icons.Filled.Favorite
    )

    object ManageApartments: BottomBarScreen(
        route = "manage_apartments",
        title = "Manage",
        icon = Icons.Filled.Edit
    )
}