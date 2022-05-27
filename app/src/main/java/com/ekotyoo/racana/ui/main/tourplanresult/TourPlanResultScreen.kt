package com.ekotyoo.racana.ui.main.tourplanresult

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RFilledButton
import com.ekotyoo.racana.core.composables.RFilledEditText
import com.ekotyoo.racana.core.composables.RLoadingOverlay
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaGray
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.data.model.DailyItem
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.data.model.getDummyTourPlan
import com.ekotyoo.racana.ui.destinations.TourPlanMapScreenDestination
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultEvent
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is TourPlanResultEvent.NavigateBackWithMessage -> {
                    resultNavigator.navigateBack(event.message)
                }
                is TourPlanResultEvent.SaveTourPlanSuccess -> {
                    modalBottomSheetState.hide()
                    snackbarHostState.showSnackbar("Berhasil menyimpan tour plan")
                }
                is TourPlanResultEvent.SaveTourPlanError -> {
                    modalBottomSheetState.hide()
                    snackbarHostState.showSnackbar("Berhasil menyimpan tour plan")
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            SaveTourPlanSheetContent(
                titleValue = state.titleTextFieldValue,
                descriptionValue = state.descriptionTextFieldValue,
                onTitleValueChange = viewModel::onTitleTextFieldValueChange,
                onDescriptionValueChange = viewModel::onDescriptionTextFieldValueChange,
                onSubmit = viewModel::onSaveTourPlanSubmitted,
                titleErrorValue = state.titleTextFieldErrorValue ?: "",
                descriptionErrorValue = state.descriptionTextFieldErrorValue ?: "",
                buttonEnabled = state.submitButtonEnabled
            )
        },
        sheetBackgroundColor = MaterialTheme.colors.primary,
    ) {
        Box(Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    RTopAppBar(
                        title = stringResource(id = R.string.detail_tour_plan),
                        isBackButtonAvailable = true,
                        onBackButtonCLicked = { navigator.navigateUp() },
                        onActionsButtonClicked = {
                            scope.launch { modalBottomSheetState.show() }
                        },
                        actionIcon = Icons.Rounded.BookmarkBorder
                    )
                }
            ) {
                TourPlanContent(
                    state = state,
                    onDateSelected = viewModel::onDateSelected,
                    onOpenMapButtonClicked = {
                        val tourPlan = state.tourPlan
                        val destination =
                            TourPlanMapScreenDestination(TourPlanMapArgument(tourPlan))
                        navigator.navigate(destination)
                    },
                    onChangePlanButtonClicked = viewModel::onChangePlanButtonClicked
                )
            }
            RLoadingOverlay(
                modifier = Modifier.align(Alignment.Center),
                visible = state.isLoading
            )
            SnackbarHost(hostState = snackbarHostState)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TourPlanContent(
    modifier: Modifier = Modifier,
    state: TourPlanResultState,
    onDateSelected: (Int) -> Unit,
    onOpenMapButtonClicked: () -> Unit,
    onChangePlanButtonClicked: () -> Unit,
) {
    Column(modifier.fillMaxSize()) {
        Spacer(Modifier.height(32.dp))
        DayHeaderSection(
            selectedDate = state.selectedDate,
            dailyList = state.tourPlan?.dailyList,
            onItemSelected = onDateSelected
        )
        Spacer(Modifier.height(16.dp))
        AnimatedContent(modifier = Modifier.weight(1f),
            targetState = state.selectedDestinationList) { targetList ->
            AttractionList(
                destinationList = targetList
            )
        }
        Spacer(Modifier.height(16.dp))
        RFilledButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            placeholderString = stringResource(id = R.string.change_tour_plan),
            onClick = onChangePlanButtonClicked
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
    isDone: Boolean,
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
    onItemSelected: (Int) -> Unit,
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
    date: String,
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

@Composable
fun SaveTourPlanSheetContent(
    titleValue: String = "",
    titleErrorValue: String = "",
    descriptionValue: String = "",
    descriptionErrorValue: String = "",
    onTitleValueChange: (String) -> Unit = {},
    onDescriptionValueChange: (String) -> Unit = {},
    buttonEnabled: Boolean = true,
    onSubmit: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    Column(Modifier.padding(horizontal = 16.dp, vertical = 32.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.save_tour_plan),
            style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.height(16.dp))

        val isTitleError by derivedStateOf { titleErrorValue.isNotEmpty() }
        SheetSection(title = stringResource(id = R.string.title)) {
            RFilledEditText(
                modifier = Modifier.fillMaxWidth(),
                value = titleValue,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                onValueChange = onTitleValueChange
            )
            AnimatedVisibility(isTitleError) {
                Text(
                    text = "Judul lebih dari 30 karakter",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        val isDescriptionError by derivedStateOf { descriptionErrorValue.isNotEmpty() }
        SheetSection(title = stringResource(id = R.string.description)) {
            RFilledEditText(
                modifier = Modifier
                    .heightIn(64.dp, 200.dp)
                    .fillMaxWidth(),
                value = descriptionValue,
                singleLine = false,
                onValueChange = onDescriptionValueChange,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    }
                )
            )
            AnimatedVisibility(isDescriptionError) {
                Text(
                    text = "Deskripsi lebih dari 200 karakter",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
        Spacer(Modifier.height(32.dp))
        RFilledButton(
            placeholderString = stringResource(id = R.string.save),
            onClick = onSubmit,
            enabled = buttonEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            )
        )
    }
}

@Composable
fun SheetSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(Modifier.height(8.dp))
        content()
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
fun SaveTourPlanSheetPreview() {
    RacanaTheme {
        SaveTourPlanSheetContent()
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
            onOpenMapButtonClicked = {},
            onChangePlanButtonClicked = {}
        )
    }
}