package com.example.rently.navigation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.rently.ui.screens.*
import com.example.rently.ui.screens.login.LoginScreen
import com.example.rently.ui.screens.thankyou.ThankYou
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Main.route,
    ) {
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
                    navController.navigate(Screen.Main.route){
                        popUpTo(Screen.Login.route){
                            inclusive = true
                        }
                    }
                },
                onSignUpClicked = {
                    navController.navigate(Screen.Signup.route)
                },
            )
        }
        composable(
            route = Screen.Signup.route,
        ) {
            SignUpScreen(
                onSignUpSuccessful = {
                navController.navigate(Screen.ThankYou.route)
            },
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.ThankYou.route,
        ){
            ThankYou(onLanded = {
                navController.navigate(Screen.Login.route){
                    popUpTo(0){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = Screen.Main.route
        ){
            MainScreen()
        }
    }
}