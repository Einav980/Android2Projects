package com.example.rently.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rently.ui.screens.DetailsScreen
import com.example.rently.ui.screens.HomeScreen
import com.example.rently.ui.screens.LoginScreen
import com.example.rently.ui.screens.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route
        ){
            DetailsScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route
        ){
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.Signup.route
        ){
            SignUpScreen(navController = navController)
        }
    }
}