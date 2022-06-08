package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.ekotyoo.racana.R

@Composable
fun RListLoadingIndicator() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.location_loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    Box(Modifier.fillMaxSize()) {
        LottieAnimation(
            modifier = Modifier
                .align(Alignment.Center)
                .size(160.dp),
            composition = composition,
            progress = progress
        )
    }
}