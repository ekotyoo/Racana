package com.ekotyoo.racana.ui.main.createtourplan

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.*
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaGray
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.destinations.TourPlanScreenDestination
import com.ekotyoo.racana.ui.main.createtourplan.model.CreateTourPlanEvent
import com.ekotyoo.racana.ui.main.createtourplan.model.CreateTourPlanState
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultArgument
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.launch
import java.time.LocalDate

@Destination(style = NavigationTransition::class)
@Composable
fun CreateTourPlanScreen(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<TourPlanScreenDestination, String?>,
    viewModel: CreateTourPlanViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {}
            is NavResult.Value -> {
                result.value?.let {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is CreateTourPlanEvent.NavigateToTourPlanResult -> {
                    navigator.navigate(
                        TourPlanScreenDestination(
                            TourPlanResultArgument(
                                totalBudget = state.totalBudgetTextFieldValue.toLong(),
                                startDate = state.selectedStartDate!!,
                                totalDestination = state.totalDestinationValue,
                            )
                        )
                    )
                }
                is CreateTourPlanEvent.SomeFieldsAreEmpty -> {
                    snackbarHostState.showSnackbar("Mohon masukkan data yang valid.")
                }
            }
        }
    }

    CreateTourPlanContent(
        onBackButtonClicked = { navigator.popBackStack() },
        onTotalBudgetTextFieldValueChange = viewModel::onTotalBudgetTextFieldValueChange,
        onDestinationIncrement = viewModel::onDestinationIncrement,
        onDestinationDecrement = viewModel::onDestinationDecrement,
        onDateSelected = viewModel::onDateSelected,
        onSubmitClicked = viewModel::onSubmitClicked,
        state = state,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateTourPlanContent(
    onBackButtonClicked: () -> Unit,
    onTotalBudgetTextFieldValueChange: (String) -> Unit,
    onDestinationIncrement: (Int) -> Unit,
    onDestinationDecrement: (Int) -> Unit,
    onDateSelected: (List<LocalDate>) -> Unit,
    onSubmitClicked: () -> Unit,
    state: CreateTourPlanState,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = { CalendarPicker(onDateSelected = onDateSelected, selectionMode = SelectionMode.Single) },
            sheetBackgroundColor = MaterialTheme.colors.primary,
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    RTopAppBar(
                        isBackButtonAvailable = true,
                        title = stringResource(id = R.string.create_tour),
                        onBackButtonCLicked = onBackButtonClicked
                    )
                },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(Modifier.height(16.dp))

                    // Budget Input
                    CreateTourPlanSection(
                        modifier = Modifier.fillMaxWidth(),
                        title = stringResource(id = R.string.total_budget)
                    ) {
                        RFilledEditText(
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = {
                                Text(
                                    text = "Rp.",
                                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                                )
                            },
                            value = state.totalBudgetTextFieldValue.toString(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = onTotalBudgetTextFieldValueChange
                        )
                    }
                    Spacer(Modifier.height(16.dp))

                    // Date Input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        CreateTourPlanSection(
                            modifier = Modifier.weight(.1f, false),
                            title = stringResource(id = R.string.pick_a_date)
                        ) {
                            val startDateEmpty by derivedStateOf {
                                state.startDateFormatted.isEmpty()
                            }
                            Text(text = if (startDateEmpty) "--" else state.startDateFormatted)
                        }
                        RIconButton(
                            imageVector = Icons.Rounded.EditCalendar,
                            contentDescription = null,
                            onClick = {
                                scope.launch {
                                    modalBottomSheetState.show()
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(16.dp))

                    // Total Destination Input
                    CreateTourPlanSection(
                        title = stringResource(id = R.string.total_destination)
                    ) {
                        Counter(
                            modifier = Modifier.width(160.dp),
                            value = state.totalDestinationValue,
                            onIncrement = onDestinationIncrement,
                            onDecrement = onDestinationDecrement
                        )
                    }

                    Spacer(Modifier.weight(1f))
                    RFilledButton(
                        placeholderString = stringResource(id = R.string.save),
                        onClick = onSubmitClicked
                    )
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
        RLoadingOverlay(
            modifier = Modifier.align(Alignment.Center),
            visible = state.isLoading
        )
        SnackbarHost(hostState = snackbarHostState)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Counter(
    modifier: Modifier = Modifier,
    value: Int,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(RacanaGray.copy(alpha = .25f))
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onDecrement(value) }) {
            Icon(
                imageVector = Icons.Rounded.Remove,
                contentDescription = stringResource(id = R.string.decrement)
            )
        }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.small)
                .size(36.dp)
                .padding(4.dp)
        ) {
            AnimatedContent(
                modifier = Modifier.align(Alignment.Center),
                targetState = value,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    }.using(SizeTransform(false))
                },
            ) { targetValue ->
                Text(
                    color = MaterialTheme.colors.onSurface,
                    text = targetValue.toString(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                )
            }
        }
        IconButton(onClick = { onIncrement(value) }) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(id = R.string.increment)
            )
        }
    }
}

@Composable
fun CreateTourPlanSection(
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
fun CreateTourPlanScreenPreview() {
    RacanaTheme {
        CreateTourPlanContent(
            onBackButtonClicked = {},
            onTotalBudgetTextFieldValueChange = {},
            onDestinationIncrement = {},
            onDestinationDecrement = {},
            onDateSelected = {},
            onSubmitClicked = {},
            state = CreateTourPlanState(),
        )
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
fun CounterPreview() {
    RacanaTheme {
        Counter(
            value = 0,
            onIncrement = {},
            onDecrement = {}
        )
    }
}