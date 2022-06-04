package com.ekotyoo.racana.core.composables

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
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

    Box(
        modifier
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth()
            .aspectRatio(2.42f)
            .clickable(onClick = onClick)
    ) {
        if (isLoading) {
            Spacer(modifier = Modifier.background(brush).fillMaxSize())
        } else {
            CoilImage(
                modifier = Modifier
                    .fillMaxSize(),
                imageModel = imageUrl,
                contentScale = ContentScale.Crop,
                previewPlaceholder = R.drawable.ic_launcher_background,
                placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
                contentDescription = null
            )
            Spacer(
                Modifier
                    .background(RacanaBlack.copy(.5f))
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
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