package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.core.theme.RacanaTheme

@Composable
fun RTopAppBar(
    title: String,
    isBackButtonAvailable: Boolean = false,
    onBackButtonCLicked: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionsButtonClicked: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface
            )
        },
        navigationIcon = {
            if (isBackButtonAvailable) {
                IconButton(
                    onClick = onBackButtonCLicked,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        painter = rememberVectorPainter(Icons.Rounded.ChevronLeft),
                        tint = MaterialTheme.colors.onPrimary,
                        contentDescription = null
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview",
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode Preview",
)
@Composable
fun RTopAppBarPreview() {
    RacanaTheme {
        RTopAppBar(title = "Preview", isBackButtonAvailable = true, {})
    }
}