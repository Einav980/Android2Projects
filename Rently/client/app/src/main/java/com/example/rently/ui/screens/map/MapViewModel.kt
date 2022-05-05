package com.example.rently.ui.screens.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MapViewModel(val static: Boolean = false): ViewModel(){
    var state = mutableStateOf(MapState())
}