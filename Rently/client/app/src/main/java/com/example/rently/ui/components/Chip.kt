package com.example.rently.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rently.ui.theme.*

@Composable
fun RentlyChip(
    text: String = "Chip",
    textStyle: TextStyle = MaterialTheme.typography.body2,
    padding: PaddingValues = PaddingValues(8.dp)
) {
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.primary
    ) {
        Row(
            modifier = Modifier
        ) {
            Text(
                text = text,
                style = textStyle,
                color = Color.White,
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
fun SquareChip(
    icon: ImageVector = Icons.Default.Home,
    size: Dp = 80.dp,
    backgroundColor: Color = MaterialTheme.colors.background,
    foregroundColor: Color = Color.White,
    text: String = "Text",
) {
    var textSize by remember { mutableStateOf(14.sp) }

    Box(
        modifier = Modifier
            .clip(RoundedSquareShape.large)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.primary,
                shape = RoundedSquareShape.large
            )
    ) {
        Column(
            modifier = Modifier
                .size(size)
                .background(backgroundColor)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Squared Chip",
                modifier = Modifier.weight(2f),
                tint = foregroundColor
            )
            Box(
                modifier = Modifier.weight(1.5f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = foregroundColor,
                    fontFamily = Varela,
                    fontSize = textSize,
                    textAlign = TextAlign.Center,
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.lineCount > 1) {
                            textSize = textSize.times(0.98f)
                        }
                    },
                    letterSpacing = 0.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ChipGroup(
    items: List<String>,
) {
    Column(
        modifier = Modifier
            .padding(8.dp), verticalArrangement = Arrangement.Center
    ) {
        LazyRow {
            items(items) {
                RentlyChip(
                    text = it,
                    textStyle = MaterialTheme.typography.body1,
                    padding = PaddingValues(start = 18.dp, end = 18.dp, top = 5.dp, bottom = 5.dp)
                )
            }
        }
    }
}


@Composable
fun OutlinedChip(
    text: String,
    icon: ImageVector? = null,
) {
    val shape = RoundedCornerShape(32.dp)
    val background = animateColorAsState(MaterialTheme.colors.background)
    val textColor = animateColorAsState(MaterialTheme.colors.primary)
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = textColor.value, shape = shape)
            .clip(shape)
            .height(32.dp)
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

@Preview
@Composable
fun ChipPreview() {
    RentlyChip()
}

@Preview
@Composable
fun SquareChipPreview() {
    RentlyTheme {
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            SquareChip(
                icon = Icons.Outlined.Bathtub,
                foregroundColor = MaterialTheme.colors.primary,
                text = "1 Bath"
            )
            SquareChip(
                icon = Icons.Outlined.Kitchen,
                foregroundColor = MaterialTheme.colors.primary,
                text = "1 Kitchen"
            )
            SquareChip(
                icon = Icons.Outlined.Bed,
                foregroundColor = MaterialTheme.colors.primary,
                text = "2 Beds"
            )
            SquareChip(
                icon = Icons.Outlined.Stairs,
                foregroundColor = MaterialTheme.colors.primary,
                text = "Floor 5"
            )
            SquareChip(
                icon = Icons.Outlined.LocalParking,
                foregroundColor = MaterialTheme.colors.primary,
                text = "Parking Included"
            )
        }
    }
}