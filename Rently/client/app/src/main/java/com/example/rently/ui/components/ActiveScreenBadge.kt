package com.example.rently.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.ui.theme.RentlyPrimaryVariantColor

@Composable
fun ActiveScreenBadge(title: String, backgroundColor: Color = MaterialTheme.colors.surface, icon: ImageVector) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(15.dp))
        .background(backgroundColor)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(imageVector = icon, contentDescription = title)
            Text(modifier = Modifier.padding(5.dp), textAlign = TextAlign.Center, text = title)
        }
    }
}

@Preview
@Composable
fun ActiveScreenBadgePreview() {
    ActiveScreenBadge(title = "Home", backgroundColor = RentlyPrimaryVariantColor, icon = Icons.Outlined.Home)
}