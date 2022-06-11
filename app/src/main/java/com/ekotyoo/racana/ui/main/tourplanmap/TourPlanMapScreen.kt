package com.ekotyoo.racana.ui.main.tourplanmap

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.DayHeaderSection
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaGreen
import com.ekotyoo.racana.core.utils.BitmapUtil
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapArgument
import com.ekotyoo.racana.ui.main.tourplanmap.model.TourPlanMapState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TourPlanMapContent(
    state: TourPlanMapState,
    onBackButtonClicked: () -> Unit,
    onDateSelected: (Int) -> Unit,
) {
    val destinationList = state.selectedDestinationList
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        destinationList.firstOrNull()?.let {
            position = CameraPosition.fromLatLngZoom(LatLng(it.lat, it.lon), 10f)
        }
    }

    LaunchedEffect(state.selectedDate) {
        destinationList.firstOrNull()?.let {
            scrollState.scrollToItem(0)
            cameraPositionState.animate(CameraUpdateFactory.newLatLng(LatLng(it.lat, it.lon)))
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
                        val position = LatLng(destination.lat, destination.lon)
                        points.add(position)
                        Marker(
                            state = MarkerState(position),
                            icon = if (destination.isDone) BitmapUtil.markerBitmapDescriptor(context = LocalContext.current,
                                R.drawable.map_marker_done,
                                null
                            ) else BitmapUtil.markerBitmapDescriptor(context = LocalContext.current,
                                R.drawable.map_marker,
                                i + 1
                            ),
                            onClick = {
                                scope.launch { scrollState.animateScrollToItem(i) }
                                false
                            },
                            title = destination.name
                        )
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
                    AnimatedContent(targetState = destinationList) { targetList ->
                        LazyRow(
                            state = scrollState,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(targetList) { item ->
                                MapDestinationCard(
                                    imageUrl = item.imageUrl,
                                    title = item.name,
                                    location = item.address,
                                    onClick = {
                                        scope.launch {
                                            cameraPositionState.animate(CameraUpdateFactory.newLatLng(
                                                LatLng(item.lat, item.lon))
                                            )
                                        }
                                    }
                                )
                            }
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun MapDestinationCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    location: String,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .size(width = 320.dp, height = 140.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
    ) {
        Row {
            CoilImage(
                modifier = Modifier
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                previewPlaceholder = R.drawable.ic_launcher_background,
                placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
                contentDescription = null,
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Row {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Rounded.Place,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = location,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body2.copy(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            )
                        )
                        Spacer(Modifier.width(24.dp))
                    }
                }
            }
        }
    }
}


