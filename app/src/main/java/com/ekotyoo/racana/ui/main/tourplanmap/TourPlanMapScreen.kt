package com.ekotyoo.racana.ui.main.tourplanmap

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaGreen
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.core.utils.BitmapUtil
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapArgument
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapState
import com.ekotyoo.racana.ui.main.tourplanresult.DayHeaderSection
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@Destination(
    style = NavigationTransition::class,
    navArgsDelegate = TourPlanMapArgument::class
)
@Composable
fun TourPlanMapScreen(
    navigator: DestinationsNavigator,
    viewModel: TourPlanMapViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    TourPlanMapContent(
        state = state,
        onBackButtonClicked = {
            navigator.navigateUp()
        },
        onDateSelected = viewModel::onDateSelected
    )
}

@Composable
fun TourPlanMapContent(
    state: TourPlanMapState,
    onBackButtonClicked: () -> Unit,
    onDateSelected: (Int) -> Unit,
) {
    val destinationList = state.selectedDestinationList
    val cameraPositionState = rememberCameraPositionState {
        destinationList.first().let {
            if (it.lat != null && it.lon != null) {
                position = CameraPosition.fromLatLngZoom(LatLng(it.lat, it.lon), 10f)
            }
        }
    }

    LaunchedEffect(state.selectedDate) {
        destinationList.first().let {
            if (it.lat != null && it.lon != null) {
                cameraPositionState.animate(CameraUpdateFactory.newLatLng(LatLng(it.lat, it.lon)))
            }
        }
    }

    Surface {
        Box(Modifier.fillMaxSize()) {
            Column {
                GoogleMap(
                    modifier = Modifier.weight(2f),
                    cameraPositionState = cameraPositionState
                ) {
                    val points = mutableListOf<LatLng>()
                    destinationList.forEachIndexed { i, destination ->
                        if (destination.lat != null && destination.lon != null) {
                            val position = LatLng(destination.lat, destination.lon)
                            points.add(position)
                            Marker(
                                state = MarkerState(position),
                                icon = BitmapUtil.markerBitmapDescriptor(context = LocalContext.current,
                                    R.drawable.map_marker,
                                    i + 1
                                ),
                                title = destination.name
                            )
                        }
                    }
                    Polyline(
                        points = points,
                        width = 20f,
                        color = RacanaGreen,
                        startCap = RoundCap(),
                        endCap = RoundCap()
                    )
                }
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .weight(1f)
                ) {
                    Spacer(Modifier.height(16.dp))
                    DayHeaderSection(
                        dailyList = state.tourPlan?.dailyList,
                        selectedDate = state.selectedDate,
                        onItemSelected = onDateSelected
                    )
                    Spacer(Modifier.height(16.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(destinationList.size) {
                            val item = destinationList[it]
                            MapDestinationCard(imageUrl = item.imageUrl)
                        }
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
            RIconButton(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                iconModifier = Modifier.size(36.dp),
                imageVector = Icons.Rounded.ChevronLeft,
                contentDescription = stringResource(id = R.string.back_button),
                onClick = onBackButtonClicked
            )
        }
    }
}

@Composable
fun MapDestinationCard(modifier: Modifier = Modifier, imageUrl: String) {
    Card(modifier.size(width = 320.dp, height = 140.dp)) {
        Row {
            CoilImage(
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                previewPlaceholder = R.drawable.ic_launcher_background,
                contentDescription = null,
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
fun MapDestinationCardPreview() {
    RacanaTheme {
        MapDestinationCard(imageUrl = "")
    }
}