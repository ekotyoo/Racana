package com.ekotyoo.racana.ui.main.tourplanresult

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RFilledButton
import com.ekotyoo.racana.core.composables.RLoadingOverlay
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaGray
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.destinations.TourPlanMapScreenDestination
import com.ekotyoo.racana.ui.main.dashboard.model.TravelDestination
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.skydoves.landscapist.coil.CoilImage

@Destination(
    style = NavigationTransition::class,
    navArgsDelegate = TourPlanResultArgument::class
)
@Composable
fun TourPlanScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String?>,
    viewModel: TourPlanResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is TourPlanResultEvent.NavigateBackWithMessage -> {
                    resultNavigator.navigateBack(event.message)
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                RTopAppBar(
                    title = stringResource(id = R.string.detail_tour_plan),
                    isBackButtonAvailable = true,
                    actionIcon = Icons.Rounded.BookmarkBorder
                )
            }
        ) {
            TourPlanContent(
                state = state,
                onDateSelected = viewModel::onDateSelected,
                onOpenMapButtonClicked = {
                    val tourPlan = state.tourPlan
                    val destination = TourPlanMapScreenDestination(TourPlanMapArgument(tourPlan))
                    navigator.navigate(destination)
                }
            )
        }
        RLoadingOverlay(
            modifier = Modifier.align(Alignment.Center),
            visible = state.isLoading
        )
    }
}

@Composable
fun TourPlanContent(
    modifier: Modifier = Modifier,
    state: TourPlanResultState,
    onDateSelected: (Int) -> Unit,
    onOpenMapButtonClicked: () -> Unit,
) {
    Column(modifier.fillMaxSize()) {
        Spacer(Modifier.height(32.dp))
        DayHeaderSection(
            selectedDate = state.selectedDate,
            dailyList = state.tourPlan?.dailyList,
            onItemSelected = onDateSelected
        )
        Spacer(Modifier.height(16.dp))
        AttractionList(
            modifier = Modifier.weight(1f),
            destinationList = state.selectedDestinationList
        )
        Spacer(Modifier.height(16.dp))
        RFilledButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            placeholderString = stringResource(id = R.string.change_tour_plan),
            onClick = {}
        )
        Spacer(Modifier.height(16.dp))
        RFilledButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            placeholderString = stringResource(id = R.string.open_map_view),
            onClick = onOpenMapButtonClicked
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun AttractionList(
    modifier: Modifier = Modifier,
    destinationList: List<TravelDestination>?,
) {
    val items = destinationList ?: emptyList()

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items.size) {
            val destination = items[it]
            AttractionCard(
                imageUrl = destination.imageUrl,
                title = destination.name,
                location = destination.location,
                isDone = destination.isDone
            )
        }
    }
}

@Composable
fun ProgressLine(modifier: Modifier = Modifier, isDone: Boolean) {
    val primaryColor = if (isDone) MaterialTheme.colors.primary else RacanaGray
    val backgroundColor = MaterialTheme.colors.background

    Canvas(
        modifier = modifier
            .size(96.dp)
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2

        drawLine(
            color = primaryColor,
            start = Offset(x = centerX, y = 0f),
            end = Offset(x = centerX, y = height),
            cap = StrokeCap.Round,
            strokeWidth = 6f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f), 16f)
        )

        if (isDone) {
            val size = 40f
            drawCircle(
                color = primaryColor,
                radius = size,
                center = Offset(x = centerX, y = centerY),
            )
            drawPath(
                color = backgroundColor,
                style = Stroke(
                    width = size / 4,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                ),
                path = Path().apply {
                    moveTo(centerX - (size / 2), centerY - (size / 10))
                    lineTo(centerX - (size / 10), centerY + (size / 3))
                    lineTo(centerX + (size / 2), centerY - (size / 3))
                },
            )
        } else {
            drawCircle(
                color = primaryColor,
                radius = 20f,
                center = Offset(x = centerX, y = centerY),
            )
            drawCircle(
                color = backgroundColor,
                radius = 14f,
                center = Offset(x = centerX, y = centerY),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AttractionCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    location: String,
    isDone: Boolean
) {
    Row {
        ProgressLine(isDone = isDone)
        Card(
            modifier = modifier
                .padding(vertical = 8.dp)
                .height(80.dp),
            onClick = {},
            elevation = 8.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                CoilImage(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .sizeIn(64.dp, 80.dp)
                        .clip(MaterialTheme.shapes.small),
                    imageModel = imageUrl,
                    contentScale = ContentScale.Crop,
                    previewPlaceholder = R.drawable.ic_launcher_background,
                    contentDescription = null,
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(text = title, style = MaterialTheme.typography.subtitle1)
                    Text(text = "Expense", style = MaterialTheme.typography.caption)
                    Text(text = location, style = MaterialTheme.typography.caption)
                }
            }
        }
    }
}

@Composable
fun DayHeaderSection(
    dailyList: List<DailyItem>?,
    selectedDate: Int,
    onItemSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        val items = dailyList ?: emptyList()
        items(items.size) {
            DayHeaderContainer(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onItemSelected(it)
                    },
                isSelected = it == selectedDate,
                dayTitle = "Hari-${items[it].number}",
                date = items[it].dateFormatted
            )
        }
    }
}

@Composable
fun DayHeaderContainer(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    dayTitle: String,
    date: String
) {
    Column(
        modifier
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    ) {
        val color =
            if (isSelected) MaterialTheme.colors.onPrimary else RacanaGray
        Text(text = dayTitle, style = MaterialTheme.typography.subtitle1, color = color)
        Text(text = date, style = MaterialTheme.typography.body2, color = color)

    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode Preview"
)
@Composable
fun TourPlanScreenPreview() {
    RacanaTheme {
        TourPlanContent(
            state = TourPlanResultState(tourPlan = getDummyTourPlan()),
            onDateSelected = {},
            onOpenMapButtonClicked = {}
        )
    }
}