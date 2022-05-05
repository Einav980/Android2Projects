package com.example.rently.ui.screens.map

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@Composable
fun Map(viewModel: MapViewModel = MapViewModel(), static: Boolean = false, modifier: Modifier = Modifier.fillMaxSize(), latLng: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 17f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = viewModel.state.value.properties,
        uiSettings = if (static) viewModel.state.value.staticMapUiSettings else MapUiSettings(),
    ) {
        Marker(
            state = MarkerState(position = latLng),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}
