package com.ekotyoo.racana.ui.home.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.BottomNavGraph
import com.ekotyoo.racana.core.composables.RDestinationCard
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.composables.RImageCard
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@BottomNavGraph(start = true)
@Destination
@Composable
fun MainScreen(navigator: DestinationsNavigator, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeAppBar {}
        }
    ) {
        MainContent(
            destinations = state.destinations,
        )
    }
}

@Composable
fun MainContent(
    destinations: List<TravelDestination>,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState(), true)
    ) {
        HomeHeader()
        Spacer(Modifier.height(16.dp))
        HomeSection(
            title = stringResource(id = R.string.top_destination)
        ) {
            DestinationRow(destinations = destinations, onItemClick = {})
        }
        Spacer(Modifier.height(16.dp))
        HomeSection(
            title = stringResource(id = R.string.traveler_stories),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                repeat(4) {
                    RImageCard(
                        imageUrl = "https://picsum.photos/200/300",
                        title = "Lorem Ipsum Dolor",
                        description = "Lorem ipsum dolor dolr asdf das",
                        onClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun HomeAppBar(
    onSearchClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hi, John",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = stringResource(id = R.string.enjoy_your_tour),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onPrimary
            )
        }
        RIconButton(
            imageVector = Icons.Rounded.Search,
            onClick = onSearchClicked
        )
    }
}

@Composable
fun HomeHeader() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.current_tour),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(Modifier.height(16.dp))
            CurrentTourPlanCard(
                modifier = Modifier.height(240.dp),
                imageUrl = "https://picsum.photos/200/300",
                title = "Travel to Madura",
                date = "16 Mei 2022 - 18 Mei 2022",
                location = "Jawa Timur"
            )
        }
    }
}

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    title: String,
    seeAllVisible: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            if (seeAllVisible) {
                Text(
                    text = stringResource(id = R.string.see_all),
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        content()
    }
}

@Composable
fun DestinationRow(
    modifier: Modifier = Modifier,
    destinations: List<TravelDestination>,
    onItemClick: (TravelDestination) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun CurrentTourPlanCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    date: String,
    location: String,
) {
    Card(modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            CoilImage(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.small)
                    .fillMaxWidth(),
                imageModel = imageUrl,
                previewPlaceholder = R.drawable.ic_launcher_background,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Rounded.Place,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = location,
                            style = MaterialTheme.typography.body2.copy(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            ),
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = date,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Preview(
    name = "Light Mode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    heightDp = 220
)
@Preview(
    name = "Dark Mode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    heightDp = 220
)
@Composable
fun CurrentTourPlanCardPreview() {
    RacanaTheme {
        CurrentTourPlanCard(
            imageUrl = "",
            title = "Travel to Madura",
            location = "Jawa Timur",
            date = "16 Mei 2022 - 18 Mei 2022"
        )
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
            MainContent(getDummyDestination())
        }
    }
}

data class TravelDestination(
    val name: String,
    val imageUrl: String,
    val location: String
)