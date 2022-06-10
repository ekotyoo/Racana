package com.ekotyoo.racana.ui.main.tourplanresult

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.*
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.destinations.DestinationDetailScreenDestination
import com.ekotyoo.racana.ui.destinations.MainScreenDestination
import com.ekotyoo.racana.ui.destinations.TourPlanMapScreenDestination
import com.ekotyoo.racana.ui.main.Action
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultArgument
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultEvent
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.ResultBackNavigator
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
                    navigator.navigate(MainScreenDestination(
                        Action.SAVE_TOUR_PLAN_SUCCESS)) {
                        popUpTo(MainScreenDestination) {
                            inclusive = true
                        }
                    }
                }
                is TourPlanResultEvent.SaveTourPlanError -> {
                    modalBottomSheetState.hide()
                    snackbarHostState.showSnackbar("Berhasil menyimpan tour plan")
                }
                is TourPlanResultEvent.NavigateToDestinationDetail -> {
                    navigator.navigate(DestinationDetailScreenDestination(event.id))
                }
                TourPlanResultEvent.PredictDestinationError -> {}
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
                    onAddDestinationClick = if(state.predictCounter < 2) viewModel::onAddDestinationClick else null,
                    onDestinationClicked = viewModel::navigateToDestinationDetail,
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
    onAddDestinationClick: (() -> Unit)?,
    onDestinationClicked: (Int) -> Unit,
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
                destinationList = targetList,
                onClick = onDestinationClicked,
                onAddDestinationClick = onAddDestinationClick
            )
        }
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