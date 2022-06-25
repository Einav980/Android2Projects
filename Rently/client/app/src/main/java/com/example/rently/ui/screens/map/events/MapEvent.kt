package com.example.rently.ui.screens.map.events

import com.google.android.gms.maps.model.LatLng

sealed class MapEvent{
    data class CameraPositionChanged(val latLng: LatLng): MapEvent()
}
