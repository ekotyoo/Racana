package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaTheme

@Composable
fun RTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    topAppBarColor: Color = MaterialTheme.colors.surface,
    textColor: Color = MaterialTheme.colors.onSurface,
    isBackButtonAvailable: Boolean = false,
    onBackButtonCLicked: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionsButtonClicked: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = topAppBarColor),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                color = textColor
            )
        },
        navigationIcon = {
            if (isBackButtonAvailable) {
                RIconButton(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    iconModifier = Modifier.size(36.dp),
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back_button),
                    onClick = onBackButtonCLicked
                )
            }
        },
        actions = {
            if (actionIcon != null) {
                RIconButton(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    imageVector = actionIcon,
                    contentDescription = null,
                    onClick = onActionsButtonClicked
                )
            }
        }
    )
}