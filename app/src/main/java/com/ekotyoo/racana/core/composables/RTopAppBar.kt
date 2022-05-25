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
    isBackButtonAvailable: Boolean = false,
    onBackButtonCLicked: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionsButtonClicked: () -> Unit = {},
    topAppBarColor: Color = MaterialTheme.colors.background,
    textColor: Color = MaterialTheme.colors.onBackground
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp),
        //.background(color = topAppBarColor, shape = RectangleShape),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                color = textColor
            )
        },
        navigationIcon = {
            if (isBackButtonAvailable) {
                RIconButton(
                    iconModifier = Modifier.size(36.dp),
                    imageVector = Icons.Rounded.ChevronLeft,
                    contentDescription = stringResource(id = R.string.back_button),
                    onClick = onBackButtonCLicked
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = topAppBarColor
        ),
        actions = {
            if (actionIcon != null) {
                RIconButton(
                    imageVector = actionIcon,
                    contentDescription = null,
                    onClick = onActionsButtonClicked
                )
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
        RTopAppBar(title = "Preview", isBackButtonAvailable = true)
    }
}