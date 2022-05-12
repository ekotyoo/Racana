package com.ekotyoo.racana.ui.home.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ekotyoo.racana.core.composables.BottomNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@BottomNavGraph(start = true)
@Destination
@Composable
fun MainScreen(navigator: DestinationsNavigator) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        MainContent()
    }
}

@Composable
fun MainContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Main")
    }
}
