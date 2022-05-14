package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaTheme

@OptIn(ExperimentalTextApi::class)
@Composable
fun RDestinationCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 10.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .clickable(onClick = onClick),
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.medium),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .placeholder(R.drawable.dummy_image)
                        .build()
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(Modifier.height(8.dp))
            Text(text = "Candi Bali", style = MaterialTheme.typography.body1)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Place,
                    tint = MaterialTheme.colors.onSurface.copy(alpha = .7f),
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Bali",
                    style = MaterialTheme.typography.body2.copy(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        ),
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    ),
                    color = MaterialTheme.colors.onSurface.copy(alpha = .5f)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode Preview",
    heightDp = 179,
    widthDp = 156
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode Preview",
    heightDp = 179,
    widthDp = 156,
)
@Composable
fun RDestinationCardPreview() {
    RacanaTheme {
        RDestinationCard(imageUrl = dummy_image, onClick = {})
    }
}

const val dummy_image = ""