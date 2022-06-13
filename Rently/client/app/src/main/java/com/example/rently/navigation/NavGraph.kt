package com.example.rently.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.rently.SharedViewModel
import com.example.rently.model.Apartment
import com.example.rently.ui.screens.*
import com.example.rently.ui.screens.add_apartment.AddApartmentScreen
import com.example.rently.ui.screens.admin_screens.manage_types.ManageApartmentTypeScreen
import com.example.rently.ui.screens.apartments.ApartmentsScreen
import com.example.rently.ui.screens.login.LoginScreen
import com.example.rently.ui.screens.map.MapScreen
import com.example.rently.ui.screens.single_apartment.NewSingleApartmentScreen
import com.example.rently.ui.screens.single_apartment.SingleApartmentScreen
import com.example.rently.ui.screens.splash.SplashScreen
import com.example.rently.ui.screens.thankyou.ThankYou
import com.example.rently.util.Constants
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            route = Screen.Details.route
        ) {
            DetailsScreen(navController = navController)
        }
        composable(
            route = Screen.MainPage.route
        ) {
            MainScreen(onFloatingButtonClicked = {
                navController.navigate(Screen.Splash.route)
            },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.MainPage.route) {
                            inclusive = true
                        }
                    }
                })
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(
                onLoginSuccessful = {
                    navController.navigate(Screen.MainPage.route) {
                        popUpTo(Screen.Login.route) {
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
        ) {
            ThankYou(
                onLanded = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
            )
            BackHandler(true) {
                // Do nothing
            }
        }

        composable(route = Screen.NewSingleApartment.route){
            val apartment = Constants.apartment
            NewSingleApartmentScreen(apartment = apartment)
        }
        composable(
            route = Screen.ManageApartmentType.route
        ) {
            ManageApartmentTypeScreen(onAddClicked = {})
        }
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(onSplashEnds = {
                navController.popBackStack()
                navController.navigate(it)
            })
        }
        composable(
            route = Screen.AddApartment.route
        ){
            AddApartmentScreen()
        }
    }
}