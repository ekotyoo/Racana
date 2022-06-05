package com.ekotyoo.racana.ui.main.listdestination

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RDestinationCard
import com.ekotyoo.racana.core.composables.RListDestination
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.ui.main.dashboard.CurrentTourPlanCard
import com.ekotyoo.racana.ui.main.destinationdetail.model.getDummyDetailDestination
import com.ekotyoo.racana.ui.main.tourplanresult.model.getDummyTourPlanResultArgument
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = NavigationTransition::class)
@Composable
fun ListDestinationScreen(
    navigator: DestinationsNavigator,
    viewModel: ListDestinationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect{ event ->
            when(event) {

            }
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                RTopAppBar(
                    title = stringResource(id = R.string.top_destination),
                    isBackButtonAvailable = true,
                    onBackButtonCLicked = { navigator.popBackStack() }
                )
            }
        ) {
            RListDestination(
                destinations = state.destinations,
                onDestinationClicked = viewModel::onDestinationClicked
            )
        }
    }
}