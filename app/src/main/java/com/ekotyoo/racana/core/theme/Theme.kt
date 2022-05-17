package com.ekotyoo.racana.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import timber.log.Timber

private val DarkColorPalette = darkColors(
    primary = ROrange,
    primaryVariant = RDarkOrange,
    secondary = RDarkBlueSurface,
    background = RDarkBlue,
    surface = RDarkBlueSurface
)

private val LightColorPalette = lightColors(
    primary = ROrange,
    primaryVariant = RDarkOrange,
    secondary = RLightOrange,
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