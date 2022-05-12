package com.ekotyoo.racana.ui.home.profile

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

@BottomNavGraph
@Destination
@Composable
fun ProfileScreen(navigator: DestinationsNavigator) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        ProfileContent()
    }
}

@Composable
fun ProfileContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile")
    }
}