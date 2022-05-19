package com.ekotyoo.racana.ui.home.create_tour_plan

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.ui.NavigationTransition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = NavigationTransition::class)
@Composable
fun CreateTourPlanScreen(
    navigator: DestinationsNavigator,
    viewModel: CreateTourPlanViewModel = hiltViewModel()
) {
    CreateTourPlanContent(
        onBackButtonClicked = { navigator.popBackStack() }
    )
}

@Composable
fun CreateTourPlanContent(onBackButtonClicked: () -> Unit) {
    Scaffold(
        topBar = {
            RTopAppBar(
                isBackButtonAvailable = true,
                title = stringResource(id = R.string.create_tour),
                onBackButtonCLicked = onBackButtonClicked
            )
        }
    ) {
        Text(stringResource(id = R.string.create_tour))
    }
}