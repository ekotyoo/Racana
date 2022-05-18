package com.ekotyoo.racana.core.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ekotyoo.racana.R

val poppinsFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
)

val balooFamily = FontFamily(
    Font(R.font.baloo_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = balooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    h5 = TextStyle(
        fontFamily = balooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = balooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
    ),
)