package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.ui.main.listdestination.getDummyDestination

@Composable
fun RListDestination(
    modifier: Modifier = Modifier,
    destinations: List<TravelDestination>,
    onDestinationClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(destinations, key = { it.id }){ destination ->
                RDestinationCard(
                    modifier = Modifier.padding(8.dp),
                    name = destination.name,
                    imageUrl = destination.imageUrl,
                    location = destination.city,
                    onClick = { onDestinationClicked(destination.id) }
                )
            }
        }
    )
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
fun CurrentTourPlanCardPreview() {
    RacanaTheme {
        RListDestination(
            destinations = getDummyDestination(),
            onDestinationClicked = {}
        )
    }
}