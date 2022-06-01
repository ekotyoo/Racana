package com.ekotyoo.racana.ui.main.search

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.composables.REditText
import com.ekotyoo.racana.core.composables.RTopAppBar
import com.ekotyoo.racana.core.navigation.BottomNavGraph
import com.ekotyoo.racana.core.navigation.NavigationTransition
import com.ekotyoo.racana.core.navigation.RootNavigator
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.ekotyoo.racana.ui.main.createtourplan.model.getCategories
import com.ramcosta.composedestinations.annotation.Destination

@BottomNavGraph
@Destination(style = NavigationTransition::class)
@Composable
fun SearchScreen(
    rootNavigator: RootNavigator,
) {
    Box(Modifier.fillMaxSize()) {
        SearchContent()
    }
}

@Composable
fun SearchContent() {
    Scaffold(
        topBar = { RTopAppBar(title = stringResource(id = R.string.search)) }
    ) {
        Column {
            Spacer(Modifier.height(16.dp))
            REditText(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = "",
                placeholderString = stringResource(id = R.string.search_destination),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search,
                        contentDescription = null)
                },
                onValueChange = {}
            )
            Spacer(Modifier.height(16.dp))
            SearchChipRow()
        }
    }
}

@Composable
fun SearchChipRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(getCategories()) { category ->
            RChip(text = category.title, filled = false)
        }
    }
}

@Composable
fun RChip(text: String, filled: Boolean) {
    val backgroundColor =
        if (filled) MaterialTheme.colors.primary else MaterialTheme.colors.background
    val textColor = if (filled) MaterialTheme.colors.background else MaterialTheme.colors.primary

    Text(
        text = text,
        color = textColor,
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .border(1.5.dp, color = textColor, shape = MaterialTheme.shapes.small)
            .background(color = backgroundColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun SearchScreenPreview() {
    RacanaTheme {
        SearchContent()
    }
}