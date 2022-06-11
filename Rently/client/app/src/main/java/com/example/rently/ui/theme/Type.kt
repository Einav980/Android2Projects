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

val Varela = FontFamily(
    Font(R.font.varela_round_regular)
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
        fontFamily = Varela,
        fontSize = 48.sp,
        fontWeight = FontWeight.Normal
    ),
    h4 = TextStyle(
        fontFamily = Varela,
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal
    ),
    h5 = TextStyle(
        fontFamily = Varela,
        fontSize = 26.sp,
        fontWeight = FontWeight.Normal
    ),
    h6 = TextStyle(
        fontFamily = Varela,
        fontSize = 22.sp,
        fontWeight = FontWeight.Normal
    ),
    subtitle1 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = RentlySubtitleTextColor,
    ),
    subtitle2 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
    )
)
val RentlyApartmentCardTypography = Typography(
    h1 = TextStyle(
        fontSize = 82.sp,
        fontFamily = Varela,
    ),
    h3 = TextStyle(
        fontSize = 48.sp,
        fontFamily = Varela,
    ),
    h5 = TextStyle(
        fontFamily = Varela,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
      fontFamily = Varela,
      fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontSize = 12.sp,
        fontFamily = Varela
    ),
    subtitle2 = TextStyle(
        fontSize = 16.sp,
        fontFamily = Varela
    )

)