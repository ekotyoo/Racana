package com.ekotyoo.racana.core.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaGray
import com.ekotyoo.racana.data.model.DailyItem
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.ui.main.tourplanresult.model.TourPlanResultState
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RDetailTourPlan(
    modifier: Modifier = Modifier,
    state: TourPlanResultState,
    onDateSelected: (Int) -> Unit,
    onDestinationClicked: (Int) -> Unit,
    onDeleteButtonClicked: () -> Unit,
) {
    Column(modifier) {
        Spacer(Modifier.height(32.dp))
        DayHeaderSection(
            selectedDate = state.selectedDate,
            dailyList = state.tourPlan?.dailyList,
            onItemSelected = onDateSelected
        )
        Spacer(Modifier.height(16.dp))
        AnimatedContent(modifier = Modifier.weight(1f),
            targetState = state.selectedDestinationList) { targetList ->
            AttractionList(
                destinationList = targetList,
                onClick = onDestinationClicked,
                onDelete = onDeleteButtonClicked
            )
        }
    }
}

@Composable
fun AttractionList(
    modifier: Modifier = Modifier,
    destinationList: List<TravelDestination>?,
    onClick: (Int) -> Unit,
    onDelete: (() -> Unit)? = null,
) {
    val items = destinationList ?: emptyList()
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        items(items) { destination ->
            AttractionCard(
                imageUrl = destination.imageUrl,
                title = destination.name,
                location = destination.address,
                isDone = destination.isDone,
                onClick = {
                    onClick(destination.id)
                },
                onDelete = onDelete
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
    isDone: Boolean,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null,
) {
    Row {
        ProgressLine(
            isDone = isDone,
            modifier = Modifier.weight(0.2f)
        )
        Column(
            modifier = Modifier.weight(0.8f)
        ) {
            Card(
                modifier = modifier
                    .aspectRatio(3.63f),
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
                        previewPlaceholder = R.drawable.ic_launcher_background,
                        contentDescription = null,
                    )
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(text = title, style = MaterialTheme.typography.subtitle1)
                        Text(text = "Expense", style = MaterialTheme.typography.caption)
                        Text(text = location, style = MaterialTheme.typography.caption)
                    }
                    if (onDelete != null) {
                        RIconButton(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            onClick = onDelete,
                            background = MaterialTheme.colors.surface,
                            tint = MaterialTheme.colors.error
                        )
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
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                dayTitle = "Hari-${item.number}",
                date = item.dateFormatted
            )
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