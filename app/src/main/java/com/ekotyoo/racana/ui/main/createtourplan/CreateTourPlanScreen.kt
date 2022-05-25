package com.ekotyoo.racana.ui.main.createtourplan

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.*
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaBlack
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
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch
import java.time.LocalDate

@Destination(style = NavigationTransition::class)
@Composable
fun CreateTourPlanScreen(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<TourPlanScreenDestination, String?>,
    viewModel: CreateTourPlanViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    resultRecipient.onNavResult { result ->
        when(result) {
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
                                city = state.selectedCity,
                                totalBudget = state.totalBudgetTextFieldValue.toLong(),
                                startDate = state.selectedStartDate,
                                endDate = state.selectedEndDate,
                                totalDestination = state.totalDestinationValue,
                                category = state.selectedCategory
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
        onCitiesTextFieldValueChange = viewModel::onCitiesTextFieldValueChange,
        onCityTextFieldCleared = viewModel::onCityTextFieldCleared,
        onCitySelected = viewModel::onCitySelected,
        onTotalBudgetTextFieldValueChange = viewModel::onTotalBudgetTextFieldValueChange,
        onDestinationIncrement = viewModel::onDestinationIncrement,
        onDestinationDecrement = viewModel::onDestinationDecrement,
        onDateSelected = viewModel::onDateSelected,
        onSubmitClicked = viewModel::onSubmitClicked,
        onCategorySelected = viewModel::onCategorySelected,
        state = state,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateTourPlanContent(
    onBackButtonClicked: () -> Unit,
    onCitiesTextFieldValueChange: (String) -> Unit,
    onCityTextFieldCleared: () -> Unit,
    onCitySelected: (String) -> Unit,
    onTotalBudgetTextFieldValueChange: (String) -> Unit,
    onDestinationIncrement: (Int) -> Unit,
    onDestinationDecrement: (Int) -> Unit,
    onDateSelected: (List<LocalDate>) -> Unit,
    onCategorySelected: (Int) -> Unit,
    onSubmitClicked: () -> Unit,
    state: CreateTourPlanState,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        ModalBottomSheetLayout(
            sheetState = modalBottomSheetState,
            sheetContent = { CalendarPicker(onDateSelected) },
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
                        .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    // City Input
                    CreateTourPlanSection(title = "Kota") {
                        CityDropdown(
                            cityResult = state.citiesResult,
                            selectedCity = state.selectedCity,
                            cityTextFieldValue = state.cityTextFieldValue,
                            onCityTextFieldCleared = onCityTextFieldCleared,
                            onCitiesTextFieldValueChange = onCitiesTextFieldValueChange,
                            onCitySelected = onCitySelected
                        )
                    }
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
                            val endDateEmpty by derivedStateOf {
                                state.endDateFormatted.isEmpty()
                            }
                            Text(text = if (startDateEmpty && endDateEmpty) "--" else state.startDateFormatted + " - " + state.endDateFormatted)
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
                    Spacer(Modifier.height(16.dp))

                    // Category Input
                    CreateTourPlanSection(title = stringResource(id = R.string.destination_category)) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            content = {
                                items(state.categories.size) {
                                    val category = state.categories[it]
                                    CategoryCard(
                                        imageUrl = category.imageUrl,
                                        title = category.title,
                                        onClick = {
                                            onCategorySelected(it)
                                        },
                                        isChecked = state.selectedCategory == it
                                    )
                                }
                            }
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
fun CityDropdown(
    cityResult: List<Pair<String, String>>,
    selectedCity: String,
    cityTextFieldValue: String,
    onCityTextFieldCleared: () -> Unit,
    onCitiesTextFieldValueChange: (String) -> Unit,
    onCitySelected: (String) -> Unit
) {
    var citiesDropdownVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .border(
                width = 1.5.dp,
                color = RacanaGray.copy(alpha = .25f),
                shape = MaterialTheme.shapes.small,
            )
            .clip(MaterialTheme.shapes.small)
            .clickable { citiesDropdownVisible = !citiesDropdownVisible }
            .background(RacanaGray.copy(alpha = .25f))
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp)
    ) {
        val selectedCityEmpty = derivedStateOf {
            selectedCity.isEmpty()
        }
        Text(text = if (selectedCityEmpty.value) "--Pilih Kota--" else selectedCity)
        AnimatedContent(
            modifier = Modifier.align(Alignment.CenterEnd),
            targetState = citiesDropdownVisible
        ) { state ->
            when (state) {
                true -> Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = null
                )
                false -> Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    }
    AnimatedVisibility(
        visible = citiesDropdownVisible,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
    ) {
        Column {
            Spacer(Modifier.height(8.dp))
            RFilledEditText(
                modifier = Modifier.fillMaxWidth(),
                value = cityTextFieldValue,
                placeholderString = "Cari Kota...",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (cityTextFieldValue.isNotEmpty()) {
                        IconButton(onClick = onCityTextFieldCleared) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null
                            )
                        }
                    }
                },
                onValueChange = onCitiesTextFieldValueChange
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 200.dp)
            ) {
                items(cityResult.size) {
                    val (city, province) = cityResult[it]
                    DropdownMenuItem(
                        modifier = Modifier.clip(MaterialTheme.shapes.small),
                        onClick = {
                            onCitySelected(city)
                            focusManager.clearFocus()
                            citiesDropdownVisible = false
                        }
                    ) {
                        Text(
                            text = "$city - $province",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    onClick: () -> Unit,
    isChecked: Boolean = false
) {
    Box(
        modifier
            .clip(MaterialTheme.shapes.small)
            .height(64.dp)
            .clickable(onClick = onClick)
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxSize(),
            imageModel = imageUrl,
            contentScale = ContentScale.Crop,
            previewPlaceholder = R.drawable.ic_launcher_background,
            contentDescription = null
        )
        Spacer(
            Modifier
                .background(RacanaBlack.copy(.5f))
                .fillMaxSize()
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            text = title,
            style = MaterialTheme.typography.subtitle2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colors.onPrimary
        )
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(CircleShape)
                .background(if (isChecked) MaterialTheme.colors.primary else Color.Transparent)
                .size(22.dp)
                .border(
                    width = 1.5.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colors.background
                )
        ) {
            if (isChecked) {
                Icon(
                    painter = rememberVectorPainter(Icons.Rounded.Check),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = null
                )
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Counter(
    modifier: Modifier = Modifier,
    value: Int,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit
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
    content: @Composable () -> Unit
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
            onCitiesTextFieldValueChange = {},
            onCityTextFieldCleared = {},
            onCitySelected = {},
            onTotalBudgetTextFieldValueChange = {},
            onDestinationIncrement = {},
            onDestinationDecrement = {},
            onDateSelected = {},
            onCategorySelected = {},
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