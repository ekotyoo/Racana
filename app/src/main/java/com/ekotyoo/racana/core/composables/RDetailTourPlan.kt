package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaGray
import com.ekotyoo.racana.core.theme.RacanaRed
import com.ekotyoo.racana.core.theme.RacanaWhite
import com.ekotyoo.racana.core.utils.currencyFormatter
import com.ekotyoo.racana.data.model.DailyItem
import com.ekotyoo.racana.data.model.TravelDestination
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun AttractionList(
    modifier: Modifier = Modifier,
    destinationList: List<TravelDestination>?,
    onClick: (Int) -> Unit,
    onDelete: ((Int) -> Unit)? = null,
    onToggleDone: ((Int) -> Unit)? = null,
    onAddDestinationClick: (() -> Unit)? = null,
) {
    val items = destinationList ?: emptyList()
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items) { destination ->
            AttractionCard(
                imageUrl = destination.imageUrl,
                title = destination.name,
                location = destination.city,
                expense = destination.weekdayPrice,
                isDone = destination.isDone,
                onClick = {
                    onClick(destination.id)
                },
                onDelete = if (onDelete != null) {
                    { onDelete(destination.id) }
                } else null,
                onToggleDone = if (onToggleDone != null) {
                    { onToggleDone(destination.id) }
                } else null
            )
        }
        onAddDestinationClick?.let {
            item {
                AddDestinationButton(onAddDestinationClick)
            }
        }
    }
}

@Composable
fun AddDestinationButton(
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(64.dp)) {
        Spacer(Modifier
            .weight(0.2f)
            .aspectRatio(1f))
        Box(
            modifier = Modifier
                .weight(0.8f),
        ) {
            val color = MaterialTheme.colors.primary
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = color,
                    style = Stroke(
                        width = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f), 16f)
                    ),
                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx())
                )
            }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.add_destination),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.primary,
            )
        }
    }
}

@Composable
fun ProgressLine(modifier: Modifier = Modifier, isDone: Boolean) {
    val primaryColor = if (isDone) MaterialTheme.colors.primary else RacanaGray
    val backgroundColor = MaterialTheme.colors.background

    Canvas(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(0.9f)
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2

        val fullHeight = (size.height * 1.23).toFloat()

        drawLine(
            color = primaryColor,
            start = Offset(x = centerX, y = 0f),
            end = Offset(x = centerX, y = fullHeight),
            cap = StrokeCap.Round,
            strokeWidth = 6f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f), 16f)
        )

        if (isDone) {
            val size = 40f
            drawCircle(
                color = primaryColor,
                radius = size,
                center = Offset(x = centerX, y = centerY),
            )
            drawPath(
                color = backgroundColor,
                style = Stroke(
                    width = size / 4,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                ),
                path = Path().apply {
                    moveTo(centerX - (size / 2), centerY - (size / 10))
                    lineTo(centerX - (size / 10), centerY + (size / 3))
                    lineTo(centerX + (size / 2), centerY - (size / 3))
                },
            )
        } else {
            drawCircle(
                color = primaryColor,
                radius = 20f,
                center = Offset(x = centerX, y = centerY),
            )
            drawCircle(
                color = backgroundColor,
                radius = 14f,
                center = Offset(x = centerX, y = centerY),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AttractionCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    location: String,
    expense: Int,
    isDone: Boolean,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null,
    onToggleDone: (() -> Unit)? = null,
) {
    Row {
        ProgressLine(
            isDone = isDone,
            modifier = Modifier.weight(0.2f)
        )
        Column(
            modifier = Modifier.weight(0.8f)
        ) {
            if (onDelete != null) {
                SwipeToDismiss(
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.8f) },
                    state = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                onDelete.invoke()
                            }
                            true
                        }
                    ), background = {
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .fillMaxSize()
                                .background(RacanaRed)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 16.dp),
                                imageVector = Icons.Rounded.Delete,
                                tint = RacanaWhite,
                                contentDescription = null
                            )
                        }
                    }
                ) {
                    Card(
                        modifier = modifier
                            .aspectRatio(3.4f),
                        onClick = onClick,
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CoilImage(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .fillMaxHeight()
                                    .clip(MaterialTheme.shapes.small),
                                imageModel = imageUrl,
                                contentScale = ContentScale.Crop,
                                placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
                                previewPlaceholder = R.drawable.ic_launcher_background,
                                contentDescription = null,
                            )
                            Spacer(Modifier.width(8.dp))
                            Column(Modifier.weight(1f)) {
                                Text(text = title,
                                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Text(text = currencyFormatter(expense),
                                    style = MaterialTheme.typography.body2)
                                Text(text = location, style = MaterialTheme.typography.body2)
                            }
                            Row {
                                if (onToggleDone != null) {
                                    RIconButton(
                                        imageVector = if (isDone) Icons.Default.Cancel else Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        onClick = onToggleDone,
                                        background = MaterialTheme.colors.surface,
                                        tint = MaterialTheme.colors.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Card(
                    modifier = modifier
                        .aspectRatio(3.4f),
                    onClick = onClick,
                    elevation = 8.dp,
                    shape = MaterialTheme.shapes.small
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoilImage(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxHeight()
                                .clip(MaterialTheme.shapes.small),
                            imageModel = imageUrl,
                            contentScale = ContentScale.Crop,
                            placeHolder = ImageBitmap.imageResource(id = R.drawable.image_placeholder),
                            previewPlaceholder = R.drawable.ic_launcher_background,
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(text = title,
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                            Text(text = currencyFormatter(expense),
                                style = MaterialTheme.typography.body2)
                            Text(text = location, style = MaterialTheme.typography.body2)
                        }
                        Row {
                            if (onToggleDone != null) {
                                RIconButton(
                                    imageVector = if (isDone) Icons.Rounded.Cancel else Icons.Rounded.CheckCircle,
                                    contentDescription = null,
                                    onClick = onToggleDone,
                                    background = MaterialTheme.colors.surface,
                                    tint = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DayHeaderSection(
    dailyList: List<DailyItem>?,
    selectedDate: Int,
    onItemSelected: (Int) -> Unit,
    onAddDateButtonClicked: (() -> Unit)? = null,
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        val items = dailyList ?: emptyList()
        itemsIndexed(items) { i, item ->
            DayHeaderContainer(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable {
                        onItemSelected(i)
                    },
                isSelected = i == selectedDate,
                dayTitle = "Hari-${i + 1}",
                date = item.dateFormatted
            )
        }
        onAddDateButtonClicked?.let {
            item {
                IconButton(onClick = it,
                    modifier = Modifier.background(MaterialTheme.colors.secondary,
                        shape = CircleShape)) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun DayHeaderContainer(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    dayTitle: String,
    date: String,
) {
    Column(
        modifier
            .background(
                color = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    ) {
        val color =
            if (isSelected) MaterialTheme.colors.onPrimary else RacanaGray
        Text(text = dayTitle, style = MaterialTheme.typography.subtitle1, color = color)
        Text(text = date, style = MaterialTheme.typography.body2, color = color)

    }
}