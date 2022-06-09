package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ekotyoo.racana.R
import com.ekotyoo.racana.core.theme.RacanaGray
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun CalendarPicker(
    modifier: Modifier = Modifier,
    onDateSelected: (List<LocalDate>) -> Unit,
    selectionMode: SelectionMode = SelectionMode.Period,
) {
    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = selectionMode
    )

    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.pick_a_date),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary)
        Spacer(Modifier.height(16.dp))
        Surface(Modifier.clip(MaterialTheme.shapes.large)) {
            SelectableCalendar(
                calendarState = calendarState,
                dayContent = {
                    DayContainer(it, onClick = {
                        onDateSelected(calendarState.selectionState.selection)
                    })
                },
                weekHeader = { WeekHeader(it) },
                monthHeader = { MonthHeader(monthState = it) }
            )
        }
    }
}

@Composable
fun <T : SelectionState> DayContainer(
    state: DayState<T>,
    modifier: Modifier = Modifier,
    currentDayColor: Color = MaterialTheme.colors.primary,
    onClick: (LocalDate) -> Unit = {},
) {
    val date = state.date
    val selectionState = state.selectionState

    val isSelected = selectionState.isDateSelected(date)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .padding(4.dp),
        elevation = 0.dp,
        border = if (state.isCurrentDay) BorderStroke(1.5.dp, currentDayColor) else null,
        backgroundColor = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface,
    ) {
        Box(
            modifier = Modifier.clickable {
                selectionState.onDateSelected(date)
                onClick(date)
            },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = if (state.isFromCurrentMonth) MaterialTheme.colors.onSurface else RacanaGray
            )
        }
    }
}

@Composable
fun WeekHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).dropLast(1),
                modifier = modifier
                    .weight(1f)
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
fun MonthHeader(
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Rounded.ChevronLeft,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Previous",
            )
        }
        Text(
            text = monthState.currentMonth.month
                .getDisplayName(TextStyle.FULL, Locale.getDefault())
                .lowercase()
                .replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h6)
        IconButton(
            modifier = Modifier.testTag("Increment"),
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }
        ) {
            Image(
                imageVector = Icons.Rounded.ChevronRight,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Next",
            )
        }
    }
}
