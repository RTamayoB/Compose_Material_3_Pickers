package com.rtamayo.compose_material3_pickers.date

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.date.composables.CalendarInput
import com.rtamayo.compose_material3_pickers.date.composables.InputSelector
import com.rtamayo.compose_material3_pickers.date.utils.DateMapper.getMonthList
import java.time.LocalDate

//Use Ceil/Floor functions to return minus 100 years and plus 100 years
val MIN_DATE: LocalDate = LocalDate.now().minusYears(50)
val MAX_DATE: LocalDate = LocalDate.now().plusYears(50)

@Composable
fun DatePicker(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE,
    onDateSelected: (date: LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var currentDate by remember { mutableStateOf(startDate) }

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
        content = {
            DatePickerContent(
                date = currentDate,
                minDate = minDate,
                maxDate = maxDate,
                onDateChanged = {
                    currentDate = it
                }
            )
        },
    )
}

@Composable
internal fun DatePickerContent(
    date: LocalDate,
    minDate: LocalDate,
    maxDate: LocalDate,
    onDateChanged: (LocalDate) -> Unit,
) {

    var showCalendar by remember { mutableStateOf(true) }

    val monthList = getMonthList(minDate, maxDate)

    Column {
        InputSelector(
            localDate = date
        ) { isShowingCalendar ->
            showCalendar = isShowingCalendar
        }
        if(showCalendar) {
            CalendarInput(
                date = date,
                monthList = monthList,
                onDateChanged = onDateChanged
            )
        }
        else {
            DateInputPicker(
                date = date,
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

@Preview
@Composable
private fun DatePickerPreview() {
    DatePicker(
        onDateSelected = {

        },
        onDismissRequest = {

        }
    )
}