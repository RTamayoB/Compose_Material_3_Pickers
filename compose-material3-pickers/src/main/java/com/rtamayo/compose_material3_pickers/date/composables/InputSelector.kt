package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.R
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.formatDate
import com.rtamayo.compose_material3_pickers.date.utils.DatePickerState
import com.rtamayo.compose_material3_pickers.date.utils.rememberDatePickerState
import java.time.LocalDate

@Composable
internal fun InputSelector(
    datePickerState: DatePickerState,
    onShowCalendar: () -> Unit
) {
    InputSelectorContent(
        showCalendar = datePickerState.showCalendarInput,
        dateContent = {
            Text(
                text = datePickerState.dateFormatted,
            )
        },
        onShowCalendar = onShowCalendar,
    )
}

@Composable
internal fun InputSelector(
    startDate: LocalDate,
    endDate: LocalDate,
    onShowCalendar: () -> Unit
) {
    InputSelectorContent(
        false,
        dateContent = {
            Text(
                text = "${formatDate(startDate, "MMM dd")} - ${formatDate(endDate, "MMM dd")}",
            )
        },
        onShowCalendar = onShowCalendar,
    )
}

@Composable
private fun InputSelectorContent(
    showCalendar: Boolean,
    dateContent: (@Composable () -> Unit),
    onShowCalendar: () -> Unit,
) {
    val icon = if (showCalendar) Icons.Default.Edit else Icons.Default.DateRange
    val contentDescription = stringResource(id = if (showCalendar) R.string.select_text_input else R.string.select_calendar_input)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = MaterialTheme.typography.headlineLarge
        ProvideTextStyle(value = textStyle) {
            dateContent()
        }
        Spacer(modifier = Modifier.weight(1F))
        IconButton(
            onClick = onShowCalendar,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    }
}