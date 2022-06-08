package com.ekotyoo.racana.ui.main.favoritedestination

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RListDestination
import com.ekotyoo.racana.core.composables.RListLoadingIndicator
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.ui.destinations.DestinationDetailScreenDestination
import com.ekotyoo.racana.ui.main.favoritedestination.model.FavoriteDestinationEvent
import com.ekotyoo.racana.ui.main.favoritedestination.model.FavoriteDestinationState
import com.ekotyoo.racana.ui.main.tourplanlist.TourPlanListEmpty
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = NavigationTransition::class)
@Composable
fun FavoriteDestinationScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoriteDestinationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is FavoriteDestinationEvent.NavigateToDetailDestination -> {
                    navigator.navigate(DestinationDetailScreenDestination(event.id)) {
                        launchSingleTop = true
                    }
                }
                is FavoriteDestinationEvent.GetFavoriteDestinationsError -> {
                    snackbarHostState.showSnackbar("Gagal mengambil data.")
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        FavoriteDestinationContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onBackButtonClicked = { navigator.popBackStack() },
            onDestinationClicked = viewModel::onDestinationClicked
        )
        if (state.destinations.isEmpty() && !state.isLoading) {
            TourPlanListEmpty(modifier = Modifier.align(Alignment.Center))
        }
        if (state.isLoading) {
            RListLoadingIndicator()
        }
    }
}

@Composable
fun FavoriteDestinationContent(
    state: FavoriteDestinationState,
    snackbarHostState: SnackbarHostState,
    onBackButtonClicked: () -> Unit,
    onDestinationClicked: (Int) -> Unit,
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = {
            RTopAppBar(
                title = stringResource(id = R.string.favorite_destination),
                isBackButtonAvailable = true,
                onBackButtonCLicked = onBackButtonClicked
            )
        }
    ) {
        RListDestination(
            destinations = state.destinations,
            onDestinationClicked = onDestinationClicked
        )
    }
}