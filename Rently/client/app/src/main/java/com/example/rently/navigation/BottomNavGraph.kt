package com.example.rently.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rently.ui.screens.ProfileScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Apartments.route
    ){
        composable(
            route = BottomBarScreen.Apartments.route,
        ){
            ApartmentsScreen()
        }
        composable(
            route = BottomBarScreen.Profile.route,
        ){
            ProfileScreen()
        }
        composable(
            route = BottomBarScreen.Settings.route,
        ){
//            SettingsScreen()
        }
    }
}