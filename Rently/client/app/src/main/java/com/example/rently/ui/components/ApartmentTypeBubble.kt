package com.example.rently.ui.components

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.outlined.Grass
import androidx.compose.material.icons.outlined.OutdoorGrill
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ApartmentTypeBubble(
    label: String,
    icon: ImageVector,
    size: Dp = 50.dp,
    backgroundColor: Color = MaterialTheme.colors.primary,
    foregroundColor: Color = MaterialTheme.colors.primaryVariant,
    active: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .defaultMinSize(size)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 100,
                    easing = LinearOutSlowInEasing
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = icon.name)
            if (active) {
                Text(text = label, color = foregroundColor)
            }
        }
    }
}

@Preview
@Composable
fun ApartmentTypeBubblePreview() {
    var active by remember { mutableStateOf(false)}
    ApartmentTypeBubble(label = "Garden", icon = Icons.Filled.Grass, active = active, onClick = {active = !active})
}