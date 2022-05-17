package com.ekotyoo.racana.core.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun RCircularProgressOverlay(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(visible, modifier) {
        Box(modifier = Modifier.pointerInput(Unit) {}) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(.5f))
            )
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}