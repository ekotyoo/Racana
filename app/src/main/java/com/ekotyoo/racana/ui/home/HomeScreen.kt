package com.ekotyoo.racana.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ekotyoo.racana.core.composables.RBottomNavigationBar
import com.ekotyoo.racana.ui.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val scaffoldState = rememberScaffoldState()
    val bottomAppBarNavController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            RBottomNavigationBar(
                navigator = navigator,
                bottomAppBarNavController = bottomAppBarNavController
            )
        }
    ) { contentPadding ->
        DestinationsNavHost(
            modifier = Modifier.padding(contentPadding),
            navGraph = NavGraphs.bottom,
            navController = bottomAppBarNavController
        )
    }
}