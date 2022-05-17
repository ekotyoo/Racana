package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun RTopAppBar(
    title: String,
    isBackButtonAvailable: Boolean = false,
    onBackButtonCLicked: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionsButtonClicked: () -> Unit ={}
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(16.dp, 8.dp),
        title = { Text(
            text = title,
            style = MaterialTheme.typography.subtitle1
        ) },
        navigationIcon = {
            if (isBackButtonAvailable) {
                IconButton(
                    onClick = {
                        // TODO: Implement back button pressed functionality
                    },
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .size(36.dp)
                ) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.ArrowBack),
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = ""
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colors.background
        ),
        actions = {
            if (actionIcon != null) {
                IconButton(
                    onClick = onActionsButtonClicked,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .size(36.dp)
                ) {
                    Icon(
                        painter = rememberVectorPainter(actionIcon),
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}