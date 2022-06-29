package com.example.rently.navigation

sealed class Screen(val route: String){
    object Login: Screen(route = "login_screen")
    object Signup: Screen(route = "signup_screen")
    object MainPage: Screen(route = "main_screen")
    object Apartments: Screen(route = "apartments_screen")
    object SingleApartment: Screen(route = "single_apartment_screen")
    object ManageApartmentType: Screen(route = "manage_apartment_screen")
    object Filter: Screen(route = "filter_screen")
    object Splash: Screen(route = "splash_screen")
    object Map: Screen(route = "map_screen/{lat}/{lng}"){
        fun passLatLng(lat: Double, lng: Double): String{
            return "map_screen/$lat/$lng"
        }
    }
    object AddApartment: Screen(route = "add_apartment_screen")

}
