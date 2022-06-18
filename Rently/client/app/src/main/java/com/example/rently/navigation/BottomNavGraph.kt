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
import com.example.rently.SharedViewModel
import com.example.rently.ui.screens.DetailsScreen
import com.example.rently.ui.screens.manageApartments.ManageApartmentsScreen
import com.example.rently.ui.screens.ProfileScreen
import com.example.rently.ui.screens.add_apartment.AddApartmentScreen
import com.example.rently.ui.screens.admin_screens.manage_types.ManageApartmentTypeScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.map.MapScreen
import com.example.rently.ui.screens.single_apartment.NewSingleApartmentScreen
import com.example.rently.ui.screens.single_apartment.SingleApartmentScreen
import com.google.android.gms.maps.model.LatLng

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {

    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Apartments.route
    ) {
        composable(
            route = BottomBarScreen.Apartments.route,
        ) {
            ApartmentsScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(
            route = BottomBarScreen.Profile.route,
        ) {
            ProfileScreen()
        }

        composable(
            route = BottomBarScreen.Watchlist.route,
        ) {
            DetailsScreen(navController = navController)
        }

        composable(
            route = BottomBarScreen.ManageApartments.route,
        ) {
            ManageApartmentsScreen(
                navController = navController,
                sharedViewModel = sharedViewModel,
            )
        }

        composable(
            route = BottomBarScreen.AdminManageApartments.route,
        ) {
            DetailsScreen(navController = navController)
        }

        composable(
            route = BottomBarScreen.AdminManageApartmentsType.route,
        ) {
            ManageApartmentTypeScreen()
        }

        composable(route = Screen.SingleApartment.route) {
            SingleApartmentScreen(
                navController = navController,
                sharedViewModel = sharedViewModel
            )
        }

        composable(route = Screen.NewSingleApartment.route) {
            NewSingleApartmentScreen(
                sharedViewModel = sharedViewModel
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
        composable(
            Screen.AddApartment.route
        ){
            AddApartmentScreen()
        }
    }
}