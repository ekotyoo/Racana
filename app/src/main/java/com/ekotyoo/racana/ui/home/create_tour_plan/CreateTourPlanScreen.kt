package com.ekotyoo.racana.ui.home.create_tour_plan

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RFilledButton
import com.ekotyoo.racana.core.composables.RFilledEditText
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.theme.RacanaBlack
import com.ekotyoo.racana.core.theme.RacanaGray
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.NavigationTransition
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@Destination(style = NavigationTransition::class)
@Composable
fun CreateTourPlanScreen(
    navigator: DestinationsNavigator,
    viewModel: CreateTourPlanViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is CreateTourPlanScreenEvent.Test -> snackbarHostState.showSnackbar(event.message)
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
        totalDestination = state.value.totalDestinationValue,
        cityTextFieldValue = state.value.cityTextFieldValue,
        citiesResult = state.value.citiesResult,
        selectedCity = state.value.selectedCity,
        totalBudgetTextFieldValue = state.value.totalBudgetTextFieldValue,
        categories = state.value.categories
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreateTourPlanContent(
    onBackButtonClicked: () -> Unit,
    onCitiesTextFieldValueChange: (String) -> Unit,
    onCityTextFieldCleared: () -> Unit,
    onCitySelected: (String) -> Unit,
    onTotalBudgetTextFieldValueChange: (String) -> Unit,
    onDestinationIncrement: (Int) -> Unit,
    onDestinationDecrement: (Int) -> Unit,
    totalDestination: Int,
    cityTextFieldValue: String,
    totalBudgetTextFieldValue: Int,
    citiesResult: List<Pair<String, String>>,
    selectedCity: String,
    categories: List<DestinationCategory>
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            RTopAppBar(
                isBackButtonAvailable = true,
                title = stringResource(id = R.string.create_tour),
                onBackButtonCLicked = onBackButtonClicked
            )
        }
    ) {
        Column(
            modifier = Modifier
                .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            CreateTourPlanSection(title = "Kota") {
                var citiesDropdownVisible by remember { mutableStateOf(false) }
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
                    Text(text = selectedCity)
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
                Spacer(Modifier.height(8.dp))
                AnimatedVisibility(visible = citiesDropdownVisible) {
                    Column {
                        RFilledEditText(
                            modifier = Modifier.fillMaxWidth(),
                            value = cityTextFieldValue,
                            placeholderString = "Cari Kota...",
                            leadingIcon = null,
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
                            items(citiesResult.size) {
                                val (city, province) = citiesResult[it]
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
            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                CreateTourPlanSection(
                    modifier = Modifier.weight(.1f, false),
                    title = "Mulai"
                ) {
                    RFilledEditText(
                        value = "",
                        readOnly = true,
                        placeholderString = "",
                        leadingIcon = null,
                        onValueChange = {}
                    )
                }
                CreateTourPlanSection(
                    modifier = Modifier.weight(.1f, false),
                    title = "Berakhir"
                ) {
                    RFilledEditText(
                        value = "",
                        readOnly = true,
                        placeholderString = "",
                        leadingIcon = null,
                        onValueChange = {}
                    )
                }
                RIconButton(
                    imageVector = Icons.Rounded.EditCalendar,
                    contentDescription = null,
                    onClick = {}
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
            ) {
                CreateTourPlanSection(
                    modifier = Modifier.weight(1f),
                    title = "Jumlah Budget"
                ) {
                    RFilledEditText(
                        value = totalBudgetTextFieldValue.toString(),
                        placeholderString = "",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        leadingIcon = null,
                        onValueChange = onTotalBudgetTextFieldValueChange
                    )
                }
                Spacer(Modifier.width(16.dp))
                CreateTourPlanSection(
                    modifier = Modifier.weight(1f),
                    title = "Jumlah Destinasi"
                ) {
                    Counter(
                        modifier = Modifier.fillMaxWidth(),
                        value = totalDestination,
                        onIncrement = onDestinationIncrement,
                        onDecrement = onDestinationDecrement
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            CreateTourPlanSection(title = "Kategori Destinasi") {
                var selectedCategory by remember {
                    mutableStateOf(0)
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        items(categories.size) {
                            val category = categories[it]
                            CategoryCard(
                                imageUrl = category.imageUrl,
                                title = category.title,
                                onClick = {
                                    selectedCategory = it
                                },
                                isChecked = selectedCategory == it
                            )
                        }
                    }
                )
            }
            Spacer(Modifier.weight(1f))
            RFilledButton(placeholderString = "Simpan", onClick = {})
            Spacer(Modifier.height(32.dp))
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
            totalDestination = 0,
            cityTextFieldValue = "",
            totalBudgetTextFieldValue = 0,
            citiesResult = emptyList(),
            selectedCity = "Jakarta",
            categories = getCategories()
        )
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