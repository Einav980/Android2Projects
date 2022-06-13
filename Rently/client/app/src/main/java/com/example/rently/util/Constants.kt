package com.example.rently.util

import com.example.rently.model.Apartment
import com.google.android.gms.maps.model.LatLng

object Constants {
    const val APP_TITLE = "Rently"
    const val BASE_URL = "http://35.234.66.166:3000/api/"
    const val DATASTORE_NAME = "settings"
    val apartment = Apartment(
        _id = "test",
        city= "Tel-Aviv",
        price = 7800,
        numberOfRooms = 3,
        address = "Dov Hauzner 2",
        numberOfBaths = 1,
        numberOfBeds = 2,
        lat = 32.110288835801384,
        lng = 34.78955024501451,
        size = 54,
        imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1"
    )
    val DefaultLocation = LatLng(31.4117257,35.0818155)
    const val GOOGLE_API_KEY = "AIzaSyD1yMYulFclTaaoWxf4rOifU-iPWqbDoS4"
}