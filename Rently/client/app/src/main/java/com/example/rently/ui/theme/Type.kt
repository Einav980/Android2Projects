package com.example.rently.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rently.R

// Set of Material typography styles to start with
val Pacifico = FontFamily(
    Font(R.font.pacifico_regular)
)

val Lobster = FontFamily(
    Font(R.font.lobster_regular)
)

val RentlyTypography = Typography(
    h1 = TextStyle(
        fontFamily = Lobster,
        fontSize = 82.sp,
        fontWeight = FontWeight.Normal
    ),
    h2 = TextStyle(
        fontFamily = Lobster,
        fontSize = 68.sp,
        fontWeight = FontWeight.Normal
    ),
    h3 = TextStyle(
        fontFamily = Lobster,
        fontSize = 48.sp,
        fontWeight = FontWeight.Normal
    ),
    h4 = TextStyle(
        fontFamily = Lobster,
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal
    ),
    h6 = TextStyle(
        fontFamily = Lobster,
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal
    )
)