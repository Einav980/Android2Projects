package com.example.rently.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.model.Apartment

@Composable
fun ApartmentCard(apartment: Apartment){
    Box(modifier = Modifier.size(120.dp)){
        Text(text = "City: ${apartment.city}")
//        Text(text = "Rooms: ${apartment.numberOfRooms}")
        Text(text = "Price: ${apartment.price}")
    }
}

@Composable
@Preview
fun ApartmentCardPreview(){
    Card(

    ) {
        var apartment = Apartment("Tel-Aviv", price = 4000000 )
        ApartmentCard(apartment = apartment)
    }

}