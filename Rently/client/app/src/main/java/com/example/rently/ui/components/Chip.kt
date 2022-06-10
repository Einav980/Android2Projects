package com.example.rently.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    name: String = "Chip",
    textStyle: TextStyle = MaterialTheme.typography.body2,
    padding: PaddingValues = PaddingValues(8.dp)
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.primary
    ) {
        Row(modifier = Modifier
        ) {
            Text(
                text = name,
                style = textStyle,
                color = Color.White,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Preview
@Composable
fun ChipPreview() {
    Chip()
}