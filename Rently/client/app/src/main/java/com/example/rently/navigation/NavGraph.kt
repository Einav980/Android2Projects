package com.example.rently.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rently.SharedViewModel
import com.example.rently.ui.screens.RegisterScreen
import com.example.rently.ui.screens.add_apartment.AddApartmentScreen
import com.example.rently.ui.screens.admin_screens.manage_types.ManageApartmentTypeScreen
import com.example.rently.ui.screens.apartments.ApartmentsViewModel
import com.example.rently.ui.screens.login.LoginScreen
import com.example.rently.ui.screens.main.MainScreen
import com.example.rently.ui.screens.map.MapScreen
import com.example.rently.ui.screens.single_apartment.SingleApartmentScreen
import com.example.rently.ui.screens.splash.SplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@RequiresApi(Build.VERSION_CODES.N)
@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val apartmentsSharedViewModel: ApartmentsViewModel = viewModel()
    val sharedViewModel = SharedViewModel()

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            route = Screen.MainPage.route
        ) {
            MainScreen(
                sharedViewModel = sharedViewModel,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.MainPage.route) {
                            inclusive = true
                        }
                    }
                },
                onMapClicked = {
                    navController.navigate(Screen.Map.passLatLng(0.0, 0.0))
                },
                onApartmentClicked = {
                    navController.navigate(Screen.SingleApartment.route)
                },
                onAddApartmentClicked = {
                    navController.navigate(Screen.AddApartment.route)
                },
                apartmentsSharedViewModel = apartmentsSharedViewModel
            )
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
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.MainPage.route) {
                        popUpTo(route = Screen.MainPage.route) {
                            inclusive = true
                        }
                    }
                },
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.ManageApartmentType.route
        ) {
            ManageApartmentTypeScreen(/*onAddClicked = {}*/)
        }

        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(onSplashEnds = {
                navController.navigate(it) {
                    popUpTo(Screen.Map.route)
                }
            })
        }
        composable(
            route = Screen.AddApartment.route
        ) {
            AddApartmentScreen(onAddApartmentFinished = {
                navController.navigate(Screen.MainPage.route) {
                    popUpTo(Screen.MainPage.route){
                        inclusive = true
                    }
                }
            },
                onBackClicked = { navController.popBackStack() })
        }

        composable(
            route = Screen.SingleApartment.route
        ) {
            SingleApartmentScreen(
                sharedViewModel = sharedViewModel,
                onBackClicked = { navController.popBackStack() },
                onMapClicked = {
                    navController.navigate(
                        Screen.Map.passLatLng(
                            it.latitude,
                            it.longitude
                        )
                    )
                })
        }

        composable(
            route = Screen.Map.route,
        ) {
            MapScreen(onApartmentClicked = {
                sharedViewModel.setApartment(it)
                navController.navigate(Screen.SingleApartment.route)
            }, onBackClicked = { navController.popBackStack() })
        }
    }
}