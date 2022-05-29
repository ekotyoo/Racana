package com.ekotyoo.racana.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource

private val DarkColorPalette = darkColors(
    primary = RacanaViolet,
    secondary = RacanaGray,
    background = RacanaBlack,
    surface = RacanaBlack,
    error = RacanaRed,
    onPrimary = RacanaWhite
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

    val configuration = LocalConfiguration.current
    val dimensions = if (configuration.screenWidthDp <= 480) mdpi else hdpi
    
    ProvideDimens(dimensions = dimensions) {
        MaterialTheme(
            colors = colors,
            typography = getTypography(),
            shapes = Shapes,
            content = content
        )
    }
}

object RacanaTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
}

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
    mdpi
}