package com.ekotyoo.racana.ui.home.tour_plan_list

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.BottomNavGraph
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.ui.NavigationTransition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@BottomNavGraph
@Destination(style = NavigationTransition::class)
@Composable
fun TourPlanListScreen(
    navigator: DestinationsNavigator,
    viewModel: TourPlanListViewModel = hiltViewModel()
) {
    TourPlanListContent()
}

@Composable
fun TourPlanListContent() {
    Scaffold(topBar = {
        RTopAppBar(title = stringResource(id = R.string.tour_plan_list))
    }) {
        Text(stringResource(id = R.string.tour_plan_list))
    }
}