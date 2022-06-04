package com.ekotyoo.racana.ui.main.destinationdetail

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RIconButton
import com.ekotyoo.racana.core.composables.RLoadingOverlay
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.core.utils.currencyFormatter
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationArgument
import com.ekotyoo.racana.ui.main.destinationdetail.model.DestinationDetail
import com.ekotyoo.racana.ui.main.destinationdetail.model.getDummyDetailDestination
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
import timber.log.Timber

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

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {

            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DestinationDetailContent(
            destination = state.destination,
            onBackButtonClicked = { navigator.popBackStack() },
            onFavoriteButtonClicked = viewModel::onFavoriteButtonClicked
        )
        RLoadingOverlay(modifier = Modifier.align(Alignment.Center), visible = state.isLoading)
    }
}

@Composable
fun DestinationDetailContent(
    destination: DestinationDetail,
    onBackButtonClicked: () -> Unit,
    onFavoriteButtonClicked: () -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(destination.lat, destination.lon), 10f)
    }

    LaunchedEffect(destination.lat, destination.lon) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLng(LatLng(destination.lat, destination.lon)))
    }

    Scaffold(
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
                    .aspectRatio(0.88f)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
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
                    Text(
                        text = destination.openCloseTime,
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = destination.address,
                        style = MaterialTheme.typography.body2
                    )
                }
                RIconButton(
                    imageVector = if (destination.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    onClick = onFavoriteButtonClicked
                )
            }
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = currencyFormatter(destination.ticketPrice),
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(11.dp))
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = destination.description,
                style = MaterialTheme.typography.caption
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
fun DestinationDetailPreview() {
    RacanaTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            DestinationDetailContent(
                getDummyDetailDestination(),
                {},
                {}
            )
        }
    }
}