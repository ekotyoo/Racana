package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaBlack
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun RImageCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth()
            .height(160.dp)
            .clickable(onClick = onClick)
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxSize(),
            imageModel = imageUrl,
            contentScale = ContentScale.Crop,
            previewPlaceholder = R.drawable.ic_launcher_background,
            contentDescription = null
        )
        Spacer(
            Modifier
                .background(RacanaBlack.copy(.5f))
                .fillMaxSize()
        )
        Column(
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.onPrimary
            )

                Text(
                    text = description,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colors.onPrimary.copy(.8f)
                )

        }
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
fun RImageCardPreview() {
    RacanaTheme {
        RImageCard(title = "", imageUrl = "", description = "", onClick = {})
    }
}