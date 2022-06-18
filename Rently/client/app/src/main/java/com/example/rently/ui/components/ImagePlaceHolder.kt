package com.example.rently.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun ImagePlaceHolder() {
    Image(
        modifier = Modifier.size(50.dp),
        imageVector = Icons.Filled.Image,
        contentDescription = "Image",
        colorFilter = ColorFilter.tint(Color.White),
    )
}
