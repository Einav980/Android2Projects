package com.example.rently.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rently.model.Apartment
import com.example.rently.ui.screens.ProfileScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.map.MapScreen
import com.example.rently.ui.screens.single_apartment.SingleApartmentScreen
import com.example.rently.util.Constants
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SingleApartment.route
    ) {
        composable(
            route = BottomBarScreen.Profile.route,
        ) {
            ProfileScreen()
        }
        composable(
            route = BottomBarScreen.Settings.route,
        ) {
//            SettingsScreen()
        }


        composable(
            route = Screen.Map.route,
            arguments = listOf(
                navArgument("lat") {
                },
                navArgument("lng") {
                }
            )
        ) {
            Log.d("Rently", "Lat: ${it.arguments?.get("lat").toString()}, Lng: ${it.arguments?.get("lng").toString()}")
            MapScreen()
        }
    }
}