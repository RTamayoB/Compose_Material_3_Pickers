package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Day
import com.rtamayo.compose_material3_pickers.date.utils.DateRangePickerUiState
import java.time.LocalDate
import java.time.YearMonth

@Composable
internal fun Day(
    day: Day,
    onDateSelected: (LocalDate) -> Unit,
) {
    var dayModifier = Modifier
        .clip(CircleShape)
        .size(36.dp)
        .aspectRatio(1F)

    if (day.isCurrentDay) {
        dayModifier = dayModifier
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
    }

    if(day.isSelected) {
        dayModifier = dayModifier
            .background(MaterialTheme.colorScheme.primary)
    }

    val color =
        if (!day.isInDateRange)
            Color.Gray
        else if (day.isSelected)
            Color.White
        else
            Color.Black

    IconButton(
        onClick = {
            onDateSelected(day.date)
        },
        enabled = day.isInDateRange
    ) {
        Box(
            modifier = dayModifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                color = color
            )
        }
    }
}

@Composable
internal fun Day(
    day: LocalDate,
    dateRangePickerUiState: DateRangePickerUiState,
    onDayClicked: (LocalDate) -> Unit,
    month: YearMonth,
    modifier: Modifier = Modifier
) {

    var dayModifier = Modifier
        .clip(CircleShape)
        .size(36.dp)
        .aspectRatio(1F)


    IconButton(
        onClick = {
            onDayClicked(day)
        },
    ) {
        Box(
            modifier = dayModifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}