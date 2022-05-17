package com.ekotyoo.racana.ui.home.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.BottomNavGraph
import com.ekotyoo.racana.core.composables.RDestinationCard
import com.ekotyoo.racana.core.composables.REditText
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@BottomNavGraph(start = true)
@Destination
@Composable
fun MainScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        MainContent(
            searchValue = state.searchTextFieldValue,
            destinations = state.destinations,
            onSearchTextFieldChange = viewModel::onSearchTextFieldValueChange,
            onClearSearchTextField = viewModel::onClearSearchTextField,
            onSearch = viewModel::onSearch
        )
    }
}

@Composable
fun MainContent(
    searchValue: String,
    destinations: List<TravelDestination>,
    onSearchTextFieldChange: (String) -> Unit,
    onClearSearchTextField: () -> Unit,
    onSearch: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState(), true)
    ) {
        Spacer(Modifier.height(32.dp))
        REditText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = searchValue,
            placeholderString = "Cari",
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchValue.isNotBlank()) {
                    IconButton(onClick = onClearSearchTextField) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            onValueChange = onSearchTextFieldChange,
            onSearch = onSearch
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Header")
        }
        HomeSection(
            modifier = Modifier.height(400.dp),
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.travel_destination),
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = stringResource(id = R.string.see_all),
                        style = MaterialTheme.typography.body2
                    )
                }
            },
            content = { DestinationGrid(destinations = destinations, onItemClick = {}) }
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        title()
        Spacer(Modifier.height(16.dp))
        content()
    }
}

@Composable
fun DestinationGrid(
    modifier: Modifier = Modifier,
    destinations: List<TravelDestination>,
    onItemClick: (TravelDestination) -> Unit
) {
    LazyHorizontalGrid(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        rows = GridCells.Adaptive(156.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(destinations.size) { index ->
            val destination = destinations[index]
            RDestinationCard(
                name = destination.name,
                location = destination.location,
                imageUrl = destination.imageUrl,
                onClick = { onItemClick(destination) }
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
fun MainScreenPreview() {
    RacanaTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            MainContent("", getDummyDestination(), {}, {}, {})
        }
    }
}

data class TravelDestination(
    val name: String,
    val imageUrl: String,
    val location: String
)