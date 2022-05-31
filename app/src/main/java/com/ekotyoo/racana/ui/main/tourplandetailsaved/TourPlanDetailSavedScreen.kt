package com.ekotyoo.racana.ui.main.tourplandetailsaved

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.AttractionList
import com.ekotyoo.racana.core.composables.DayHeaderSection
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.core.utils.CurrencyFormatter
import com.ekotyoo.racana.ui.destinations.DestinationDetailScreenDestination
import com.ekotyoo.racana.ui.destinations.TourPlanMapScreenDestination
import com.ekotyoo.racana.ui.main.profile.ProfileContent
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedEvent
import com.ekotyoo.racana.ui.main.tourplandetailsaved.model.TourPlanDetailSavedState
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage
import java.time.LocalDate

@Destination(style = NavigationTransition::class)
@Composable
fun TourPlanDetailSavedScreen(
    navigator: DestinationsNavigator,
    viewModel: TourPlanDetailSavedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when(event) {
                is TourPlanDetailSavedEvent.NavigateToDestinationDetail -> {
                    navigator.navigate(DestinationDetailScreenDestination)
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                RTopAppBar(
                    title = state.tourPlan.title ?: "",
                    isBackButtonAvailable = true,
                    onBackButtonCLicked = { navigator.popBackStack() },
                    actionIcon = Icons.Default.LocationOn,
                    onActionsButtonClicked = {
                        navigator.navigate(
                            TourPlanMapScreenDestination(TourPlanMapArgument(state.tourPlan))
                        )
                    }
                )
            }
        ) {
            TourPlanDetailSavedContent(
                state = state,
                onDateSelected = viewModel::onDateSelected,
                onDestinationClicked = viewModel::navigateToDestinationDetail,
                onStartTourButtonClicked = viewModel::startTourButtonClicked
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TourPlanDetailSavedContent(
    modifier: Modifier = Modifier,
    state: TourPlanDetailSavedState,
    onDateSelected: (Int) -> Unit,
    onDestinationClicked: () -> Unit,
    onStartTourButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        CoilImage(
            imageModel = state.tourPlan.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2.45f)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop,
            previewPlaceholder = R.drawable.ic_launcher_background
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(0.5f)) {
                Text(
                    text = stringResource(id = R.string.trip_date), 
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = state.tourPlan.period,
                    style = MaterialTheme.typography.caption,
                )
            }
            val budget = state.tourPlanResultArgs?.totalBudget ?: 0
            Text(
                text = CurrencyFormatter(budget),
                style = MaterialTheme.typography.subtitle2
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        //Description
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            text = state.tourPlan.description ?: "",
            style = MaterialTheme.typography.caption
        )

        //Tour Plan
        DayHeaderSection(
            selectedDate = state.selectedDate,
            dailyList = state.tourPlan.dailyList,
            onItemSelected = onDateSelected
        )
        Spacer(Modifier.height(16.dp))
        AnimatedContent(modifier = Modifier.weight(1f),
            targetState = state.selectedDestinationList) { targetList ->
            AttractionList(
                destinationList = targetList,
                onClick = onDestinationClicked
            )
        }
    }
}

@Preview(
    name = "Light Mode Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode Preview",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun TourPlanDetailSavedPreview() {
    RacanaTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            TourPlanDetailSavedContent(
                state = TourPlanDetailSavedState(
                    tourPlanResultArgs = TourPlanResultArgument(
                        "Malang",
                        1000000,
                        null,
                        null,
                        5,
                        1
                    )
                ),
                onDateSelected = {},
                onDestinationClicked = {},
                onStartTourButtonClicked = {}
            )
        }
    }
}