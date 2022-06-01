package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaTheme
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalTextApi::class)
@Composable
fun RDestinationCard(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    location: String,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val translateX = rememberInfiniteTransition().animateFloat(initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(tween(1000, easing = FastOutSlowInEasing)))

    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 0f),
        end = Offset(x = translateX.value, y = 0f)
    )

    Card(
        modifier = modifier.size(width = 156.dp, height = 179.dp),
        elevation = 10.dp,
    ) {
        if (isLoading) {
            Spacer(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(brush)
                    .fillMaxSize()
            )
        } else {
            Column(
                modifier = Modifier
                    .clickable(onClick = onClick)
                    .padding(8.dp)
            ) {
                CoilImage(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .fillMaxWidth(),
                    imageModel = imageUrl,
                    contentScale = ContentScale.Crop,
                    previewPlaceholder = R.drawable.ic_launcher_background,
                    contentDescription = null,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.subtitle2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            imageVector = Icons.Rounded.Place,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = location,
                            style = MaterialTheme.typography.body2.copy(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            ),
                        )
                    }
                }
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
        RDestinationCard(
            name = "Candi Bali",
            location = "Bali",
            imageUrl = dummy_image,
            onClick = {}
        )
    }
}

const val dummy_image = "https://picsum.photos/200"