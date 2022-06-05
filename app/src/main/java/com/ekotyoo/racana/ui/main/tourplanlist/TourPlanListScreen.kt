package com.ekotyoo.racana.ui.main.tourplanlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RPlanCard
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.BottomNavGraph
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.navigation.RootNavigator
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.ui.destinations.TourPlanDetailSavedScreenDestination
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedArgument
import com.ekotyoo.racana.ui.main.tourplanlist.model.TourPlanListEvent
import com.ramcosta.composedestinations.annotation.Destination

@BottomNavGraph
@Destination(style = NavigationTransition::class)
@Composable
fun TourPlanListScreen(
    rootNavigator: RootNavigator,
    viewModel: TourPlanListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is TourPlanListEvent.NavigateToTourPlanDetail -> {
                    rootNavigator.value.navigate(
                        TourPlanDetailSavedScreenDestination(
                            TourPlanDetailSavedArgument(event.tourPlan)
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
                TourPlanListEvent.DeleteTourPlanSuccess -> {
                    snackbarHostState.showSnackbar("Tour plan berhasil dihapus.")
                }
                TourPlanListEvent.DeleteTourPlanFailed -> {
                    snackbarHostState.showSnackbar("Gagal menghapus tour plan.")
                }
                TourPlanListEvent.GetTourPlanFailed -> {
                    snackbarHostState.showSnackbar("Gagal mengambil data.")
                }
            }
        }
    }

    val tourPlanEmpty by derivedStateOf { state.tourPlanList.isEmpty() }

    Box(Modifier.fillMaxSize()) {
        TourPlanListContent(
            tourPlanList = state.tourPlanList,
            onCardClick = viewModel::onTourPlanClicked,
            onItemDelete = viewModel::deletePlanButtonClicked,
            snackbarHostState = snackbarHostState
        )
        if (tourPlanEmpty && !state.isLoading) {
            TourPlanListEmpty(modifier = Modifier.align(Alignment.Center))
        }
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
        }
    }
}

@Composable
fun TourPlanListEmpty(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.location))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally),
            composition = composition,
            progress = progress
        )
        Text(text = stringResource(id = R.string.tour_plan_empty),
            style = MaterialTheme.typography.body1)
    }
}

@Composable
fun TourPlanListContent(
    tourPlanList: List<TourPlan>,
    onCardClick: (TourPlan) -> Unit,
    onItemDelete: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = {
            RTopAppBar(title = stringResource(id = R.string.tour_plan_list)
            )
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tourPlanList, key = { it.id ?: 0 }) { plan ->
                RPlanCard(
                    modifier = Modifier.aspectRatio(2.6f),
                    name = plan.title ?: "Untitled",
                    imageUrl = plan.imageUrl,
                    date = plan.period,
                    description = plan.description ?: "-",
                    onClick = {
                        onCardClick(plan)
                    },
                    onDelete = {
                        if (plan.id != null) {
                            onItemDelete(plan.id.toInt())
                        }
                    }
                )
            }
        }
    }
}