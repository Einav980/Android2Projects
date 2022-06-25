package com.example.rently.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rently.FilterSharedViewModel
import com.example.rently.SharedViewModel
import com.example.rently.ui.screens.manage_apartments.ManageApartmentsScreen
import com.example.rently.ui.screens.profile.ProfileScreen
import com.example.rently.ui.screens.admin_screens.adminManageApartment.AdminManageApartmentsScreen
import com.example.rently.ui.screens.admin_screens.manage_types.ManageApartmentTypeScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.filter.FilterScreen
import com.example.rently.ui.screens.map.MapScreen
import com.example.rently.ui.screens.watchList.WatchListScreen
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BottomNavGraph(
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    onApartmentClicked: () -> Unit,
    onMapClicked: () -> Unit,
    onAddApartmentClicked: () -> Unit,
    onLogout: () -> Unit
) {

    val filterSharedViewModel: FilterSharedViewModel = viewModel()

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
                filterSharedViewModel = filterSharedViewModel,
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
                navController = navController,
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
                sharedViewModel = sharedViewModel
            )
        }

        composable(
            route = BottomBarScreen.AdminManageApartmentsType.route,
        ) {
            ManageApartmentTypeScreen()
        }

        composable(route = Screen.Filter.route) {
            FilterScreen(
                navController = navController,
                filterSharedViewModel = filterSharedViewModel
            )
        }

        composable(
            route = Screen.Map.route,
            arguments = listOf(
                navArgument("lat") {
                    type = NavType.FloatType
                },
                navArgument("lng") {
                    type = NavType.FloatType
                },
            )
        ) {
            val lat = it.arguments?.getFloat("lat")!!.toDouble()
            val lng = it.arguments?.getFloat("lng")!!.toDouble()
            MapScreen(LatLng(lat, lng))
        }
    }
}