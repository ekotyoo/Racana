package com.ekotyoo.racana.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = RacanaViolet,
    secondary = RacanaGray,
    background = RacanaBlack,
    surface = RacanaBlack,
    error = RacanaRed,
    onPrimary = RacanaBlack
)

private val LightColorPalette = lightColors(
    primary = RacanaViolet,
    secondary = RacanaYellow,
    onPrimary = RacanaWhite,
    onSecondary = RacanaBlack,
    background = RacanaWhite,
    error = RacanaRed,
    onError = RacanaWhite,
    surface = RacanaWhite,
    onSurface = RacanaBlack,
    onBackground = RacanaBlack
)

@Composable
fun RacanaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}