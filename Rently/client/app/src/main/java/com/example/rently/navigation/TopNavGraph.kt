package com.example.rently.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rently.ui.screens.ProfileScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen

@Composable
fun TopNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = TopBarScreen.Apartments.route
    ){
        composable(
            route = TopBarScreen.Apartments.route,
        ){
            ApartmentsScreen(navController = navController)
        }
        composable(
            route = TopBarScreen.Profile.route,
        ){
            ProfileScreen()
        }
        composable(
            route = TopBarScreen.Settings.route,
        ){
//            SettingsScreen()
        }
        composable(
            route = TopBarScreen.Logout.route,
        ){
//            LogoutAction()
        }
    }
}