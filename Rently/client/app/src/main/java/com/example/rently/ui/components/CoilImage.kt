package com.example.rently.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role.Companion.Image
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import java.lang.reflect.Modifier

@Composable
fun CoilImage(url: String) {
    val painter = rememberAsyncImagePainter(model = url)
    Image(
        painter = painter,
        contentDescription = "Image"
    )
}