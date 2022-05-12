package com.ekotyoo.racana.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = ROrange,
    primaryVariant = RDarkOrange,
    secondary = RLightOrange,
    background = RDarkBlue
)

private val LightColorPalette = lightColors(
    primary = ROrange,
    primaryVariant = RDarkOrange,
    secondary = RLightOrange
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