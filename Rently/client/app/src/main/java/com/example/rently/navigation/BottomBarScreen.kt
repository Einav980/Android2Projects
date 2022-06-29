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
        icon = Icons.Filled.Home
    )

    object Profile: BottomBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Filled.Person
    )

    object Watchlist: BottomBarScreen(
        route = "watch_list_screen",
        title = "Watch List",
        icon = Icons.Filled.RemoveRedEye
    )

    object ManageApartments: BottomBarScreen(
        route = "manage_apartments",
        title = "Manage",
        icon = Icons.Filled.Edit
    )

    object AdminManageApartments: BottomBarScreen(
        route = "admin_manage_apartments",
        title = "Manage",
        icon = Icons.Filled.Edit
    )

    object AdminManageApartmentsType: BottomBarScreen(
        route = "admin_manage_apartments_type",
        title = "Types",
        icon = Icons.Filled.Settings
    )
}