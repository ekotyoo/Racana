package com.ekotyoo.racana.core.composables

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.core.theme.RacanaGray

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CityDropdown(
    cityResult: List<Pair<String, String>>,
    selectedCity: String,
    cityTextFieldValue: String,
    onCityTextFieldCleared: () -> Unit,
    onCitiesTextFieldValueChange: (String) -> Unit,
    onCitySelected: (String) -> Unit,
) {
    var citiesDropdownVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .border(
                width = 1.5.dp,
                color = RacanaGray.copy(alpha = .25f),
                shape = MaterialTheme.shapes.small,
            )
            .clip(MaterialTheme.shapes.small)
            .clickable { citiesDropdownVisible = !citiesDropdownVisible }
            .background(RacanaGray.copy(alpha = .25f))
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp)
    ) {
        val selectedCityEmpty = derivedStateOf {
            selectedCity.isEmpty()
        }
        Text(text = if (selectedCityEmpty.value) "--Pilih Kota--" else selectedCity)
        AnimatedContent(
            modifier = Modifier.align(Alignment.CenterEnd),
            targetState = citiesDropdownVisible
        ) { state ->
            when (state) {
                true -> Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = null
                )
                false -> Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }
    }
    AnimatedVisibility(
        visible = citiesDropdownVisible,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
    ) {
        Column {
            Spacer(Modifier.height(8.dp))
            RFilledEditText(
                modifier = Modifier.fillMaxWidth(),
                value = cityTextFieldValue,
                placeholderString = "Cari Kota...",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (cityTextFieldValue.isNotEmpty()) {
                        IconButton(onClick = onCityTextFieldCleared) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null
                            )
                        }
                    }
                },
                onValueChange = onCitiesTextFieldValueChange
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(0.dp, 200.dp)
            ) {
                items(cityResult, key = { it.first }) { item ->
                    val (city, province) = item
                    DropdownMenuItem(
                        modifier = Modifier.clip(MaterialTheme.shapes.small),
                        onClick = {
                            onCitySelected(city)
                            focusManager.clearFocus()
                            citiesDropdownVisible = false
                        }
                    ) {
                        Text(
                            text = "$city - $province",
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}