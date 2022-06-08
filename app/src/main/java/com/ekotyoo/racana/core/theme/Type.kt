package com.ekotyoo.racana.core.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
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

@JvmName("getTypography1")
@Composable
fun getTypography(): Typography {
    return Typography(
        body1 = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = RacanaTheme.dimens.sp14
        ),
        body2 = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = RacanaTheme.dimens.sp12
        ),
        caption = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = RacanaTheme.dimens.sp12
        ),
        button = TextStyle(
            fontFamily = balooFamily,
            fontWeight = FontWeight.Bold,
            fontSize = RacanaTheme.dimens.sp16
        ),
        subtitle1 = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = RacanaTheme.dimens.sp16
        ),
        subtitle2 = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = RacanaTheme.dimens.sp14
        ),
        h5 = TextStyle(
            fontFamily = balooFamily,
            fontWeight = FontWeight.Bold,
            fontSize = RacanaTheme.dimens.sp24
        ),
        h6 = TextStyle(
            fontFamily = balooFamily,
            fontWeight = FontWeight.Bold,
            fontSize = RacanaTheme.dimens.sp20,
        ),
    )
}