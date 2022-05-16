package com.ekotyoo.racana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.destinations.HomeScreenDestination
import com.ekotyoo.racana.ui.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainNavController = rememberNavController()
            val viewModel: MainActivityViewModel = hiltViewModel()

            RacanaTheme {
                LaunchedEffect(Unit) {
                    viewModel.eventChannel.collect { event ->
                        when (event) {
                            is MainActivityEvent.Unauthenticated -> {
                                mainNavController.navigate(LoginScreenDestination) {
                                    popUpTo(HomeScreenDestination) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }

                DestinationsNavHost(navGraph = NavGraphs.root, navController = mainNavController)
            }
        }
    }
}