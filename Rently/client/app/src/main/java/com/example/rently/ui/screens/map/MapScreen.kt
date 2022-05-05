package com.example.rently.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.util.Constants
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen(latLng: LatLng = Constants.DefaultLocation) {
    RentlyTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Map(latLng = latLng)
        }
    }
}