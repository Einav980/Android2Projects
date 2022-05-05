package com.example.rently.ui.screens.map

import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

class MapState {
    val properties: MapProperties = MapProperties(mapType = MapType.NORMAL, minZoomPreference = 15f)
    val staticMapUiSettings: MapUiSettings = MapUiSettings(
        compassEnabled = false,
        scrollGesturesEnabled = false,
        rotationGesturesEnabled = false,
        zoomGesturesEnabled = false,
        tiltGesturesEnabled = false,
    )
}