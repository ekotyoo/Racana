package com.ekotyoo.racana.ui.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ekotyoo.racana.core.composables.RBottomNavigationBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.navigation.RootNavigator
import com.ekotyoo.racana.ui.NavGraphs
import com.ekotyoo.racana.ui.destinations.TourPlanListScreenDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@RootNavGraph(start = true)
@Destination(style = NavigationTransition::class)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    action: Action? = null,
) {
    val scaffoldState = rememberScaffoldState()
    val snackBarHostState = SnackbarHostState()
    val bottomAppBarNavController = rememberAnimatedNavController()

    LaunchedEffect(Unit) {
        if (action != null) {
            when (action) {
                Action.SAVE_TOUR_PLAN_SUCCESS -> {
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                RBottomNavigationBar(
                    rootNavigator = navigator,
                    bottomAppBarNavController = bottomAppBarNavController
                )
            }
        ) { contentPadding ->
            DestinationsNavHost(
                modifier = Modifier.padding(contentPadding),
                navGraph = NavGraphs.bottom,
                navController = bottomAppBarNavController,
                dependenciesContainerBuilder = {
                    dependency(RootNavigator(navigator))
                },
                engine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.BottomCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                    defaultAnimationsForNestedNavGraph = mapOf(
                        NavGraphs.bottom to NestedNavGraphDefaultAnimations(
                            enterTransition = { fadeIn(animationSpec = tween(2000)) },
                            exitTransition = { fadeOut(animationSpec = tween(2000)) }
                        ),
                    )
                )
            )
        }
        SnackbarHost(hostState = snackBarHostState)
    }
}

enum class Action {
    SAVE_TOUR_PLAN_SUCCESS
}