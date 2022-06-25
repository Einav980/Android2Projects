package com.example.rently.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(7.dp),
    large = RoundedCornerShape(10.dp),
)

val RoundedSquareShape = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

val RentlyCardShape = Shapes(
    small = RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp,
        bottomEnd = 4.dp,
        bottomStart = 4.dp
    ),
    medium = RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp,
        bottomEnd = 4.dp,
        bottomStart = 4.dp
    ),
    large = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomEnd = 16.dp,
        bottomStart = 16.dp
    )
)