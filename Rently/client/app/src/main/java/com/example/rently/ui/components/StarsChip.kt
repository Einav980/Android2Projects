package com.example.rently.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rently.ui.theme.RentlyStarColor
import com.example.rently.ui.theme.RentlyTheme
import com.example.rently.ui.theme.Varela

@Composable
fun StarsChip(
    stars: Double = 0.0,
    backgroundColor: Color = MaterialTheme.colors.primary,
    foregroundColor: Color = Color.White,
    shape: Shape = RoundedCornerShape(5.dp)
) {
    Box(
        modifier = Modifier
            .clip(shape = shape)
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = RentlyStarColor
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${stars.toDouble()}",
                color = foregroundColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Varela,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun StarChipPreview() {
    RentlyTheme {
        StarsChip(3.0)
    }
}