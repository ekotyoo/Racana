package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.data.model.TravelDestination

@Composable
fun RListDestination(
    modifier: Modifier = Modifier,
    destinations: List<TravelDestination>,
    onDestinationClicked: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(destinations, key = { it.id }) { destination ->
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
