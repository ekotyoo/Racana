package com.ekotyoo.racana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.destinations.HomeScreenDestination
import com.ekotyoo.racana.ui.destinations.LoginScreenDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.state.value.isLoading
        }

        setContent {
            val mainNavController = rememberAnimatedNavController()

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

                DestinationsNavHost(navGraph = NavGraphs.root,
                    navController = mainNavController,
                    engine = rememberAnimatedNavHostEngine(
                        navHostContentAlignment = Alignment.BottomCenter,
                        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                        defaultAnimationsForNestedNavGraph = mapOf(
                            NavGraphs.root to NestedNavGraphDefaultAnimations(
                                enterTransition = { fadeIn(animationSpec = tween(2000)) },
                                exitTransition = { fadeOut(animationSpec = tween(2000)) }
                            ),
                        )
                    ))
            }
        }
    }
}