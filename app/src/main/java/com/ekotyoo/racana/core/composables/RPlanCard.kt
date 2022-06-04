package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun RPlanCard(
    modifier: Modifier = Modifier,
    name: String,
    date: String,
    description: String,
    imageUrl: String,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    var popupMenuVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .height(116.dp)
            .fillMaxWidth(),
        elevation = 8.dp,
    ) {
        Box {
            Row(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(8.dp)
            ) {
                CoilImage(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    imageModel = imageUrl,
                    contentScale = ContentScale.Crop,
                    previewPlaceholder = R.drawable.ic_launcher_background,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body2
                    )
                }
                IconButton(onClick = {
                    popupMenuVisible = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = stringResource(id = R.string.item_option_button)
                    )
                }
            }
            val configuration = LocalConfiguration.current
            val offsetX = configuration.screenWidthDp.dp
            DropdownMenu(
                expanded = popupMenuVisible,
                offset = DpOffset(x = offsetX, y = (-116).dp),
                onDismissRequest = {
                    popupMenuVisible = false
                }
            ) {
                DropdownMenuItem(onClick = {
                    onDelete()
                }) {
                    Text(
                        text = stringResource(id = R.string.delete_tour_plan),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview",
    heightDp = 116
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode Preview",
    heightDp = 116
)
@Composable
fun RPlanCardPreview() {
    RacanaTheme {
        RPlanCard(
            name = "Plan #1",
            imageUrl = "https://picsum.photos/200/300",
            onClick = {},
            date = "12 Mei 2021 - 28 Mei2022",
            description = "Ini deskripsi",
            onDelete = {}
        )
    }
}