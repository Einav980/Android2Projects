package com.example.rently.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rently.SharedViewModel
import com.example.rently.model.Apartment
import com.example.rently.ui.screens.ProfileScreen
import com.example.rently.ui.screens.admin_screens.manage_types.ManageApartmentTypeScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.main.MainScreenViewModel
import com.example.rently.ui.screens.map.MapScreen
import com.example.rently.ui.screens.single_apartment.SingleApartmentScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun TopNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {

    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = TopBarScreen.Apartments.route
    ) {
        composable(
            route = TopBarScreen.Apartments.route,
        ) {
            ApartmentsScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable(
            route = TopBarScreen.Profile.route,
        ) {
            ProfileScreen()
        }
        composable(
            route = TopBarScreen.Settings.route,
        ) {
//            SettingsScreen()
        }
        composable(
            route = TopBarScreen.Logout.route,
        ) {
//            viewModel.logoutUser()
//            onLogout()
        }
        composable(route = Screen.SingleApartment.route) {
//            LaunchedEffect(key1 = it) {
//                val apartment =
//                    navController.previousBackStackEntry?.savedStateHandle?.get<Apartment>("apartment")
//                Log.d("Rently", "Apartment: $apartment")
//            }
//            val apartment =
//                navController.previousBackStackEntry?.savedStateHandle?.get<Apartment>("apartment")!!
            SingleApartmentScreen(
                navController = navController,
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
            route = TopBarScreen.ManageApartmentTypes.route,
        ) {
            ManageApartmentTypeScreen(onAddClicked = {})
        }
    }
}