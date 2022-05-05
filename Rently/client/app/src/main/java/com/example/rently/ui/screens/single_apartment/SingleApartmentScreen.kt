package com.example.rently.ui.screens.single_apartment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rently.model.Apartment
import com.example.rently.model.User
import com.example.rently.navigation.Screen
import com.example.rently.ui.components.ApartmentImage
import com.example.rently.ui.screens.map.Map
import com.example.rently.ui.theme.RentlyApartmentCardTheme
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.Constants
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun SingleApartmentScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    val apartment = navController.previousBackStackEntry?.savedStateHandle?.get<Apartment>("apartment")

    RentlyApartmentCardTheme {
//        viewModel.getUserInfo(apartment.userId)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { /*closeScreen()*/ },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Back",
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                apartment?.address?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        text = it,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h3
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    apartment?.let { ApartmentImage(url = it?.imageUrl) }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Floor: ${apartment?.floor}",
                        style = MaterialTheme.typography.h6
                    )

                    Text(
                        text = "Apartment Number: ${apartment?.apartmentNumber}",
                        style = MaterialTheme.typography.h6
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small)
                ) {
                    apartment?.lat?.let { LatLng(it, apartment?.lng) }
                        ?.let { Map(static = true, latLng = it) }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(
                    onClick = { navController.navigate(Screen.Map.passLatLng(apartment!!.lat, apartment!!.lng))},
                    modifier = Modifier.fillMaxWidth()
                        .clip(MaterialTheme.shapes.small)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .weight(1f)
                    )
                    Text(
                        text = "See On Map",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.weight(9f)
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SingleApartmentScreenPreview() {
//    val apartment = Constants.apartment
//    SingleApartmentScreen(navController = , seeOnMapClick = {})
//}