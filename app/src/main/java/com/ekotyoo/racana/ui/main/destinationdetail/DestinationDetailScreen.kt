package com.ekotyoo.racana.ui.main.destinationdetail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.composables.RLoadingOverlay
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.core.theme.RacanaYellow
import com.ekotyoo.racana.core.utils.currencyFormatter
import com.ekotyoo.racana.ui.main.createtourplan.Counter
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationArgument
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetail
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailEvent
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetailState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.coil.CoilImage

@Destination(
    style = NavigationTransition::class,
    navArgsDelegate = DestinationArgument::class
)
@Composable
fun DestinationDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: DestinationDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = SnackbarHostState()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                DestinationDetailEvent.SaveFavoriteDestinationSuccess -> {
                    snackbarHostState.showSnackbar("Destinasi berhasil ditambahkan ke favorit.")
                }
                DestinationDetailEvent.SaveFavoriteDestinationError -> {
                    snackbarHostState.showSnackbar("Gagal menambahkan destinasi ke favorit.")
                }
                DestinationDetailEvent.UndoFavoriteDestinationSuccess -> {
                    snackbarHostState.showSnackbar("Destinasi berhasil dihapus dari favorit.")
                }
                DestinationDetailEvent.UndoFavoriteDestinationError -> {
                    snackbarHostState.showSnackbar("Gagal menghapus destinasi favorit")
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DestinationDetailContent(
            state = state,
            snackbarHostState = snackbarHostState,
            onBackButtonClicked = { navigator.popBackStack() },
            onFavoriteButtonClicked = viewModel::onFavoriteButtonClicked,
            onUndoFavoriteButtonClicked = viewModel::onUndoFavoriteButtonClicked
        )
        RLoadingOverlay(modifier = Modifier.align(Alignment.Center), visible = state.isLoading)
    }
}

@Composable
fun DestinationDetailContent(
    state: DestinationDetailState,
    snackbarHostState: SnackbarHostState,
    onBackButtonClicked: () -> Unit,
    onFavoriteButtonClicked: (Int) -> Unit,
    onUndoFavoriteButtonClicked: (Int) -> Unit,
) {
    val destination = state.destination

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(destination.lat, destination.lon), 11f)
    }

    LaunchedEffect(destination.lat, destination.lon) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(LatLng(destination.lat, destination.lon)))
    }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        topBar = {
            RTopAppBar(
                title = stringResource(id = R.string.destination_detail),
                isBackButtonAvailable = true,
                onBackButtonCLicked = onBackButtonClicked
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            CoilImage(
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
                imageModel = destination.imageUrl,
                previewPlaceholder = R.drawable.ic_launcher_background
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = destination.name,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = RacanaYellow
                        )
                        Text(
                            text = destination.rating.toString(),
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Text(
                        text = destination.openCloseTime,
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = destination.address,
                        style = MaterialTheme.typography.body2
                    )
                }
                if (state.isTogglingFavorite) {
                    IconButton(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colors.secondary),
                        onClick = {}
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.onSecondary
                        )
                    }
                } else {
                    RIconButton(
                        imageVector = if (destination.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        onClick = {
                            if (destination.isFavorite) {
                                onUndoFavoriteButtonClicked(destination.id)
                            } else {
                                onFavoriteButtonClicked(destination.id)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(
                    text = currencyFormatter(destination.ticketPrice),
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.weekday),
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal)
                )
            }
            Row {
                Text(
                    text = currencyFormatter(destination.ticketPriceWeekend),
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.weekend),
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = destination.description,
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(15.dp))
            GoogleMap(
                modifier = Modifier
                    .aspectRatio(1.88f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(LatLng(destination.lat, destination.lon))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
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
        DestinationDetailContent(
            state = DestinationDetailState(
                destination = DestinationDetail(
                    name = "Nama Tempat",
                    rating = 4.5
                )
            ),
            snackbarHostState = SnackbarHostState(),
            onBackButtonClicked = { /*TODO*/ },
            onFavoriteButtonClicked = {},
            onUndoFavoriteButtonClicked = {}
        )
    }
}