package com.rtamayo.compose_material3_pickers.date

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.rtamayo.compose_material3_pickers.FullScreenPickerDialog
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.date.composables.CalendarRangeInput
import com.rtamayo.compose_material3_pickers.date.composables.InputSelector
import com.rtamayo.compose_material3_pickers.date.utils.DateMapper
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
    FullScreenPickerDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Select Range")
        },
        confirmButton = { 
            TextButton(onClick = {
                onDateSelected(currentStartDate, currentEndDate)
            }) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            IconButton(onClick = onDismissRequest) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        content = {
            DateRangePickerContent(
                startDate = startDate,
                endDate = endDate,
                minDate = minDate,
                maxDate = maxDate,
                onDateChanged = { startDate, endDate ->
                    currentStartDate = startDate
                    currentEndDate = endDate
                },
                onInputChange = {

                }
            )
        }
    )
}

@Composable
private fun DateRangePickerContent(
    startDate: LocalDate,
    endDate: LocalDate,
    minDate: LocalDate,
    maxDate: LocalDate,
    onDateChanged: (startDate: LocalDate, endDate: LocalDate) -> Unit,
    onInputChange: (Boolean) -> Unit
) {
    var showCalendar by remember { mutableStateOf(true) }

    val monthList = DateMapper.getMonthList(minDate, maxDate)

    Column {
        InputSelector(
            startDate = startDate,
            endDate = endDate,
        ) { isShowingCalendar ->
            showCalendar = isShowingCalendar
            onInputChange(showCalendar)
        }
        if (showCalendar) {
            CalendarRangeInput(
                startDate = startDate,
                endDate = endDate,
                monthList = monthList,
                onDateChanged = onDateChanged
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TestDateRangePicker(
    startDate: LocalDate,
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE,
    onDateSelected: (date: LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {

    var currentDate by remember { mutableStateOf(startDate) }

    var showCalendar by remember {
        mutableStateOf(true)
    }
    PickerDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Select Date",
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(currentDate)
                }
            ) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = "Cancel")
            }
        },
        topConfirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(currentDate)
                }
            ) {
                Text(text = "Ok")
            }
        },
        topDismissButton = {
            IconButton(onClick = onDismissRequest) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        },
        content = {
            DateRangePickerContent(
                startDate = startDate,
                endDate = startDate,
                minDate = minDate,
                maxDate = maxDate,
                onDateChanged = { startDate, endDate ->

                },
                onInputChange = {
                    showCalendar = it
                }
            )
        },
        modifier = if (showCalendar) Modifier.fillMaxSize() else Modifier.wrapContentHeight(),
        shape = if (showCalendar) RectangleShape else AlertDialogDefaults.shape,
        showCalendar = !showCalendar,
        properties = if (showCalendar) DialogProperties(usePlatformDefaultWidth = false) else DialogProperties()
    )
}