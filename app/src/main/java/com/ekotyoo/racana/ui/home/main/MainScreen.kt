package com.ekotyoo.racana.ui.home.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.BottomNavGraph
import com.ekotyoo.racana.core.composables.RDestinationCard
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@BottomNavGraph(start = true)
@Destination
@Composable
fun MainScreen(navigator: DestinationsNavigator) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        MainContent()
    }
}

@Composable
fun MainContent() {
    val destinations = getDummyDestination().toMutableStateList()

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        DestinationGrid(destinations)
    }
}

@Composable
fun DestinationGrid(
    destinations: List<TravelDestination>
) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.travel_destination),
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = stringResource(id = R.string.see_all),
                    style = MaterialTheme.typography.body2
                )
            }
        }
        items(destinations.size) { index ->
            RDestinationCard(
                name = destinations[index].name,
                location = destinations[index].location,
                imageUrl = destinations[index].imageUrl,
                onClick = {}
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
            MainContent()
        }
    }
}

private fun getDummyDestination() = List(10) { TravelDestination("Name $it", "https://picsum.photos/200/300", "Location $it") }

data class TravelDestination(
    val name: String,
    val imageUrl: String,
    val location: String
)