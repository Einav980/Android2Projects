package com.example.rently.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rently.ui.screens.ProfileScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.login.LoginScreen
import com.example.rently.ui.screens.main.MainScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun TopNavGraph(
    navController: NavHostController,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = TopBarScreen.Apartments.route
    ) {
        composable(
            route = TopBarScreen.Apartments.route,
        ) {
            ApartmentsScreen(navController = navController)
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
            viewModel.logoutUser()
            onLogout()
        }
    }
}