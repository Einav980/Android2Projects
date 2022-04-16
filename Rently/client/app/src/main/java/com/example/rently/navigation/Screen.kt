package com.example.rently.navigation

sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Details: Screen(route = "details_screen")
    object Login: Screen(route = "login_screen")
    object Signup: Screen(route = "signup_screen")
    object ThankYou: Screen(route = "thankyou_screen")
}
