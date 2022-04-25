package com.example.rently.ui.screens.apartments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.model.Apartment
import com.example.rently.ui.components.ApartmentCard

@Composable
fun ApartmentsScreen() {
    var apartments = listOf<Apartment>(
        Apartment("Tel-Aviv", price = 7800, numberOfRooms = 3, address = "Dov Hauzner 2", numberOfBaths = 1, numberOfBeds = 2, size = 54, type = "Private", imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1"),
        Apartment("Bat Yam", price = 5500, numberOfRooms = 3, address = "רחל המשוררת 9-1", numberOfBaths = 2, numberOfBeds = 3, size = 88, type = "Private", imageUrl = "https://q-xx.bstatic.com/xdata/images/hotel/840x460/200051570.jpg?k=52c15a2aa674672a1440bd1d596e57f918b2fbdc0c942b6a5add5a6dc0f4a165"),
        Apartment("Tel-Aviv", price = 7800, numberOfRooms = 3, address = "Dov Hauzner 2", numberOfBaths = 1, numberOfBeds = 2, size = 54, type = "Private", imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/72282092.jpg?k=5eeba7eb191652ce0c0988b4c7c042f1165b7064d865b096bb48b8c48bf191b9&o=&hp=1"),
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        items(items = apartments){ apartment ->
            ApartmentCard(apartment = apartment)
            Spacer(
                modifier = Modifier.height(15.dp)
            )
        }
    }
}

@Preview
@Composable
fun ApartmentsScreenPreview() {
    ApartmentsScreen()
}