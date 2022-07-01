package com.example.rently.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rently.SharedViewModel
import com.example.rently.ui.screens.manage_apartments.ManageApartmentsScreen
import com.example.rently.ui.screens.profile.ProfileScreen
import com.example.rently.ui.screens.admin_screens.adminManageApartment.AdminManageApartmentsScreen
import com.example.rently.ui.screens.admin_screens.manage_types.ManageApartmentTypeScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.apartments.ApartmentsViewModel
import com.example.rently.ui.screens.filter.FilterScreen
import com.example.rently.ui.screens.watchList.WatchListScreen

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BottomNavGraph(
    sharedViewModel: SharedViewModel,
    apartmentsSharedViewModel: ApartmentsViewModel,
    navController: NavHostController,
    onApartmentClicked: () -> Unit,
    onMapClicked: () -> Unit,
    onAddApartmentClicked: () -> Unit,
    onLogout: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Apartments.route
    ) {
        composable(
            route = BottomBarScreen.Apartments.route,
        ) {
            ApartmentsScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                viewModel = apartmentsSharedViewModel,
                onApartmentClicked = onApartmentClicked,
                onMapClicked = onMapClicked
            )
        }
        composable(
            route = BottomBarScreen.Profile.route,
        ) {
            ProfileScreen(
                onLogout = onLogout
            )
        }

        composable(
            route = BottomBarScreen.Watchlist.route,
        ) {
            WatchListScreen(
                sharedViewModel = sharedViewModel,
                onApartmentClicked = onApartmentClicked
            )
        }

        composable(
            route = BottomBarScreen.ManageApartments.route,
        ) {
            ManageApartmentsScreen(
                onApartmentClicked = onApartmentClicked,
                sharedViewModel = sharedViewModel,
                onAddApartmentClicked = onAddApartmentClicked
            )
        }

        composable(
            route = BottomBarScreen.AdminManageApartments.route,
        ) {
            AdminManageApartmentsScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
                onApartmentClicked = onApartmentClicked
            )
        }

        composable(
            route = BottomBarScreen.AdminManageApartmentsType.route,
        ) {
            ManageApartmentTypeScreen()
        }

        composable(route = Screen.Filter.route) {
            FilterScreen(
                viewModel = apartmentsSharedViewModel,
                onFilterApplied = {
                    navController.navigate(Screen.Apartments.route) {
                        popUpTo(Screen.Apartments.route)
                    }
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}