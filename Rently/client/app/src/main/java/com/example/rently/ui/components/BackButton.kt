package com.example.rently.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.rently.ui.theme.RoundedSquareShape

@Composable
fun BackButton(
    modifier: Modifier = Modifier
        .clip(RoundedSquareShape.large)
        .background(Color.LightGray),
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    BackButton()
}