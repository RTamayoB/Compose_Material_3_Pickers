package com.rtamayo.compose_material3_pickers.date

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.R
import com.rtamayo.compose_material3_pickers.date.composables.CalendarRangeInput
import com.rtamayo.compose_material3_pickers.date.composables.InputSelector
import com.rtamayo.compose_material3_pickers.date.range.DateRangePickerState
import com.rtamayo.compose_material3_pickers.date.range.rememberDateRangePickerState
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MAX_DATE
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MIN_DATE
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.getMonthList
import java.time.LocalDate

@Composable
fun DateRangePicker(
    startDate: LocalDate = LocalDate.now(),
    endDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE,
    onDateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {

    var currentStartDate by remember { mutableStateOf(startDate) }
    var currentEndDate by remember { mutableStateOf(endDate) }
    var showCalendar by remember { mutableStateOf(true) }

    val dateRangePickerState = rememberDateRangePickerState(minDate, maxDate)

    PickerDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.select_date),
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(currentStartDate, currentEndDate)
                }
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        topConfirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(currentStartDate, currentEndDate)
                }
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        topDismissButton = {
            IconButton(onClick = onDismissRequest) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.cancel)
                )
            }
        },
        content = {
            DateRangePickerContent(
                startDate = currentStartDate,
                endDate = currentEndDate,
                minDate = minDate,
                maxDate = maxDate,
                onDateChanged = {
                    dateRangePickerState.setSelectedDay(it)
                    val uiState = dateRangePickerState.dateRangePickerUiState
                    uiState.value.selectedStartDate?.let { newStartDate ->
                        currentStartDate = newStartDate
                        Log.d("Date", startDate.toString())
                    }
                    uiState.value.selectedEndDate?.let { newEndDate ->
                        currentEndDate = newEndDate
                        Log.d("Date", endDate.toString())
                    }
                },
                onInputChange = {
                    showCalendar = it
                },
                dateRangePickerState = dateRangePickerState
            )
        },
        isFullScreen = showCalendar
    )
}

@Composable
private fun DateRangePickerContent(
    startDate: LocalDate,
    endDate: LocalDate,
    minDate: LocalDate,
    maxDate: LocalDate,
    onDateChanged: (startDate: LocalDate) -> Unit,
    onInputChange: (Boolean) -> Unit,
    dateRangePickerState: DateRangePickerState,
) {
    var showCalendar by remember { mutableStateOf(true) }

    val monthList = getMonthList(minDate, maxDate)

    Column {
        InputSelector(
            startDate = startDate,
            endDate = endDate,
        ) {
            showCalendar
            onInputChange(showCalendar)
        }
        if (showCalendar) {
            CalendarRangeInput(
                startDate = startDate,
                endDate = endDate,
                monthList = monthList,
                onDateChanged = onDateChanged,
                dateRangePickerState = dateRangePickerState
            )
        }
        else {
            DateRangeInputPicker(
                startDate = startDate,
                endDate = endDate,
                minDate = minDate,
                maxDate = maxDate,
                onDateChange = onDateChanged,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .padding(top = 16.dp),
            )
        }
    }
}