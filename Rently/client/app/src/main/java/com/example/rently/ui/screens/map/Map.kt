package com.example.rently.ui.screens.map

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("PermissionLaunchedDuringComposition")
@Composable
fun Map(
    viewModel: MapViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxSize(),
    latLng: LatLng
) {
    val context = LocalContext.current
    val state = viewModel.state
    val cameraPositionState = rememberCameraPositionState {
        state.cameraPosition
    }
    var showLoading by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is MapViewModel.ValidationEvent.LoadingApartments -> {
                    showLoading = true
                }

                is MapViewModel.ValidationEvent.LoadingFinished -> {
                    showLoading = false
                }
            }
        }
    }


    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = state.properties,
        uiSettings = state.staticMapUiSettings,
    ) {
        state.apartments.forEach { apartment ->
            Marker(
                state = MarkerState(apartment.location),
                title = apartment.status,
            )
        }
    }
    if (showLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

fun printMessage(): Boolean {
    Log.d("Rently", "Clicked")
    return true
}
