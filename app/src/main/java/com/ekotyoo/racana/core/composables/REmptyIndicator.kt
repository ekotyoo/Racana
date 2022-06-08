package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.ekotyoo.racana.R

@Composable
fun REmptyIndicator(modifier: Modifier, text: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.location))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally),
            composition = composition,
            progress = progress
        )
        Text(text = text,
            style = MaterialTheme.typography.body1)
    }
}