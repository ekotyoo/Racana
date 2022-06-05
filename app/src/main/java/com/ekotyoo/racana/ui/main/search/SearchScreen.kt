package com.ekotyoo.racana.ui.main.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RDestinationCard
import com.ekotyoo.racana.core.composables.REditText
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.BottomNavGraph
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.navigation.RootNavigator
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.destinations.DestinationDetailScreenDestination
import com.ekotyoo.racana.ui.main.createtourplan.model.DestinationCategory
import com.ekotyoo.racana.ui.main.createtourplan.model.getCategories
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationArgument
import com.ekotyoo.racana.ui.main.search.model.SearchEvent
import com.ekotyoo.racana.ui.main.search.model.SearchState
import com.ramcosta.composedestinations.annotation.Destination

@BottomNavGraph
@Destination(style = NavigationTransition::class)
@Composable
fun SearchScreen(
    rootNavigator: RootNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SearchEvent.NotFound -> snackbarHostState.showSnackbar("Destinasi tidak ditemukan.")
                is SearchEvent.Error -> snackbarHostState.showSnackbar(event.message)
                is SearchEvent.NavigateToDetail -> {
                    rootNavigator.value.navigate(
                        DestinationDetailScreenDestination(DestinationArgument(event.id))
                    ) {
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {
        SearchContent(
            state = state,
            onSearch = viewModel::onSearch,
            onQueryChange = viewModel::onQueryChange,
            onQueryClear = viewModel::onQueryClear,
            onCategoryClick = viewModel::onCategoryClick,
            onSearchResultClick = viewModel::onSearchResultClick,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun SearchContent(
    state: SearchState = SearchState(),
    onSearch: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    onQueryClear: () -> Unit = {},
    onCategoryClick: (DestinationCategory) -> Unit = {},
    onSearchResultClick: (Int) -> Unit = {},
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = { RTopAppBar(title = stringResource(id = R.string.search)) }
    ) {
        Column {
            Spacer(Modifier.height(16.dp))
            REditText(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = state.query,
                placeholderString = stringResource(id = R.string.search_destination),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (state.query.isNotEmpty()) {
                        IconButton(onClick = onQueryClear) {
                            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                onSearch = onSearch,
                onValueChange = onQueryChange
            )
            Spacer(Modifier.height(16.dp))
            SearchChipRow(
                selectedCategory = state.selectedCategory,
                onItemClick = onCategoryClick
            )
            Spacer(Modifier.height(16.dp))
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
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(state.searchResult, key = { it.id }) { destination ->
                        RDestinationCard(
                            name = destination.name,
                            imageUrl = destination.imageUrl,
                            location = destination.city,
                            onClick = {
                                onSearchResultClick(destination.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchChipRow(
    selectedCategory: DestinationCategory? = null,
    categories: List<DestinationCategory> = getCategories(),
    onItemClick: (DestinationCategory) -> Unit,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            RChip(text = category.title, filled = selectedCategory?.id == category.id, onClick = {
                onItemClick(category)
            })
        }
    }
}

@Composable
fun RChip(text: String, filled: Boolean, onClick: () -> Unit) {
    val backgroundColor =
        if (filled) MaterialTheme.colors.primary else MaterialTheme.colors.background
    val textColor = if (filled) MaterialTheme.colors.background else MaterialTheme.colors.primary

    Text(
        text = text,
        color = textColor,
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .border(1.5.dp,
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small)
            .clickable(onClick = onClick)
            .background(color = backgroundColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun SearchScreenPreview() {
    RacanaTheme {
        SearchContent()
    }
}