package com.example.rently.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.ui.theme.RentlyChipColor
import com.example.rently.ui.theme.RentlyChipTextColor

@Composable
fun OutlinedChoiceChip(
    text: String,
    icon: ImageVector? = null,
    backgroundColor: Color = RentlyChipColor,
    foregroundColor: Color = RentlyChipTextColor,
    chosen: Boolean = false,
    onSelect: () -> Unit
) {
    val shape = RoundedCornerShape(32.dp)
    val background = animateColorAsState(
        if (chosen) {
            MaterialTheme.colors.background
        } else {
            backgroundColor
        }
    )
    val textColor = animateColorAsState(
        if (chosen) {
            MaterialTheme.colors.primary
        } else {
            foregroundColor
        }
    )
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = textColor.value, shape = shape)
            .clip(shape)
            .height(32.dp)
            .clickable { onSelect() }
            .background(background.value)
            .padding(start = 12.dp, end = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "Icon", tint = textColor.value)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = textColor.value,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview("Choice Chip Unchosen")
@Composable
fun OutlinedChoiceChipPreview() {
    OutlinedChoiceChip(text = "Apartment", icon = Icons.Outlined.Apartment, onSelect = {})
}

@Preview("Choice Chip Chosen")
@Composable
fun OutlinedChoiceChipChosenPreview() {
    OutlinedChoiceChip(text = "Apartment", icon = Icons.Outlined.Apartment, onSelect = {}, chosen = true)
}