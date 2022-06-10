package com.example.rently.navigation

import android.util.Log
import com.example.rently.model.Apartment
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

sealed class Screen(val route: String){
    object Details: Screen(route = "details_screen")
    object Login: Screen(route = "login_screen")
    object Signup: Screen(route = "signup_screen")
    object ThankYou: Screen(route = "thankyou_screen")
    object MainPage: Screen(route = "main_screen")
    object Apartments: Screen(route = "apartments_screen")
    object SingleApartment: Screen(route = "single_apartment_screen/{apartment}")
    object ManageApartmentType: Screen(route = "manage_apartment_screen")
    object Splash: Screen(route = "splash_screen")
    object Map: Screen(route = "map_screen/{lat}/{lng}"){
        fun passLatLng(lat: Double, lng: Double): String{
            return "map_screen/$lat/$lng"
        }
    }
    object NewSingleApartment: Screen(route = "new_single_apartment")
}
