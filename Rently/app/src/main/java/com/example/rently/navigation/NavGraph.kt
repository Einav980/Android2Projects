package com.example.rently.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.rently.ui.screens.DetailsScreen
import com.example.rently.ui.screens.HomeScreen
import com.example.rently.ui.screens.login.LoginScreen
import com.example.rently.ui.screens.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route
        ) {
            DetailsScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(
                onLoginSuccessful = {
                    navController.navigate(Screen.Details.route){
                        popUpTo(Screen.Login.route){
                            inclusive = true
                        }
                    }
                    Log.d("Login", "Navigate to Details")
                },
                onSignUpClicked = {
                    navController.navigate(Screen.Signup.route)
                })
        }
        composable(
            route = Screen.Signup.route
        ) {
            SignUpScreen(navController = navController)
        }
    }
}