package com.example.rently.ui.screens.map.state

import com.example.rently.model.Apartment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

data class MapState(
    val properties: MapProperties = MapProperties(mapType = MapType.NORMAL),
    val staticMapUiSettings: MapUiSettings = MapUiSettings(
        compassEnabled = false,
        scrollGesturesEnabled = true,
        rotationGesturesEnabled = true,
        zoomGesturesEnabled = true,
        zoomControlsEnabled = false,
        myLocationButtonEnabled = true
    ),
    val apartments: List<Apartment> = emptyList(),
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(LatLng(30.0,0.0), 30f),
    val selectedApartment: Apartment? = null,
    val showSelectedApartment: Boolean = false,
)