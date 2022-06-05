package com.ekotyoo.racana.ui.main.listdestination

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RListDestination
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.ui.destinations.DestinationDetailScreenDestination
import com.ekotyoo.racana.ui.main.listdestination.model.ListDestinationEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = NavigationTransition::class)
@Composable
fun ListDestinationScreen(
    navigator: DestinationsNavigator,
    viewModel: ListDestinationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is ListDestinationEvent.NavigateToDetailDestination -> {
                    navigator.navigate(DestinationDetailScreenDestination(event.id)) {
                        launchSingleTop = true
                    }
                }
                is ListDestinationEvent.GetDestinationsError -> {
                    snackbarHostState.showSnackbar("Gagal mengambil data.")
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
            topBar = {
                RTopAppBar(
                    title = stringResource(id = R.string.top_destination),
                    isBackButtonAvailable = true,
                    onBackButtonCLicked = { navigator.popBackStack() }
                )
            }
        ) {
            if (state.isLoading) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.location_loading))
                val progress by animateLottieCompositionAsState(
                    composition,
                    iterations = LottieConstants.IterateForever
                )
                Box(Modifier.fillMaxSize()) {
                    LottieAnimation(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(160.dp),
                        composition = composition,
                        progress = progress
                    )
                }
            } else {
                RListDestination(
                    destinations = state.destinations,
                    onDestinationClicked = viewModel::onDestinationClicked
                )
            }
        }
    }
}