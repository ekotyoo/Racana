package com.ekotyoo.racana.ui.main.createtourplan

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.RFilledButton
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.composables.dummy_image
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.coil.CoilImage

@Destination(style = NavigationTransition::class)
@Composable
fun TourPlanScreen() {
    Scaffold(
        topBar = {
            RTopAppBar(
                title = stringResource(id = R.string.detail_tour_plan),
                isBackButtonAvailable = true,
                actionIcon = Icons.Rounded.BookmarkBorder
            )
        }
    ) {
        TourPlanContent()
    }
}

@Composable
fun TourPlanContent(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Spacer(Modifier.height(32.dp))
        DayHeaderSection()
        AttractionList()
        Spacer(Modifier.weight(1f))
        RFilledButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            placeholderString = stringResource(id = R.string.change_tour_plan),
            onClick = {}
        )
        Spacer(Modifier.height(32.dp))
    }
}

@Composable
fun AttractionList(modifier: Modifier = Modifier) {
    Row(modifier.padding(horizontal = 16.dp)) {
        Spacer(Modifier.width(80.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(5) {
                AttractionCard(imageUrl = dummy_image)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AttractionCard(modifier: Modifier = Modifier, imageUrl: String) {
    Card(
        modifier = modifier
            .height(80.dp),
        onClick = {},
        elevation = 8.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            CoilImage(
                modifier = Modifier
                    .aspectRatio(1f)
                    .sizeIn(64.dp, 80.dp)
                    .clip(MaterialTheme.shapes.small),
                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                previewPlaceholder = R.drawable.ic_launcher_background,
                contentDescription = null,
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(text = "Title", style = MaterialTheme.typography.subtitle1)
                Text(text = "Expense", style = MaterialTheme.typography.caption)
                Text(text = "Brief", style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
fun DayHeaderSection() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(3) {
            DayHeaderContainer(it == 0)
        }
    }
}

@Composable
fun DayHeaderContainer(
    isSelected: Boolean = false
) {
    Column {
        CompositionLocalProvider(LocalContentAlpha provides if (isSelected) 1f else ContentAlpha.medium) {
            Text(text = "Hari 1", style = MaterialTheme.typography.subtitle1)
            Text(text = "15/05/22", style = MaterialTheme.typography.body1)
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
fun DayHeaderContainerPreview() {
    RacanaTheme {
        DayHeaderContainer()
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
fun DayHeaderSectionPreview() {
    RacanaTheme {
        DayHeaderSection()
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
fun TourPlanScreenPreview() {
    RacanaTheme {
        TourPlanScreen()
    }
}