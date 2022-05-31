package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaTheme

@Composable
fun RIconButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    tint: Color = MaterialTheme.colors.onSecondary,
    background: Color = MaterialTheme.colors.secondary
) {
    IconButton(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(background),
        onClick = onClick
    ) {
        Icon(
            modifier = iconModifier,
            painter = rememberVectorPainter(image = imageVector),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview(
    name = "Light Mode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark Mode Preview",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun RIconButtonPreview() {
    RacanaTheme {
        RIconButton(imageVector = Icons.Rounded.Search, contentDescription =  null, onClick = {})
    }
}