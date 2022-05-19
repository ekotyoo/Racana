package com.ekotyoo.racana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.destinations.HomeScreenDestination
import com.ekotyoo.racana.ui.destinations.LoginScreenDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.state.value.isLoading
        }

        setContent {
            val mainNavController = rememberNavController()

            RacanaTheme {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = false
                val backgroundColor = MaterialTheme.colors.primary

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )
                }

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