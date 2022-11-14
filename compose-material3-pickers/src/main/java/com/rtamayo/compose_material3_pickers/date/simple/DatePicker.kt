package com.rtamayo.compose_material3_pickers.date.simple

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.R
import com.rtamayo.compose_material3_pickers.date.composables.CalendarInput
import com.rtamayo.compose_material3_pickers.date.composables.InputSelector
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MAX_DATE
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MIN_DATE
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DatePicker(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE,
    onDateSelected: (date: LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    containerColor: Color = AlertDialogDefaults.containerColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    contentColor: Color = AlertDialogDefaults.textContentColor,
) {

    val datePickerState = rememberDatePickerState(startDate, minDate, maxDate)

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
                    onDateSelected(datePickerState.selectedDate)
                },
                modifier = Modifier.height(36.dp)
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.height(36.dp)
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        content = {
            DatePickerContent(
                datePickerState = datePickerState,
                onDateChanged = {
                    datePickerState.setDate(it)
                }
            )
        },
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        contentColor = contentColor
    )
}

@Composable
internal fun DatePickerContent(
    datePickerState: DatePickerState,
    onDateChanged: (LocalDate) -> Unit,
) {
    Column {
        InputSelector(
            datePickerState = datePickerState
        ) {
            datePickerState.toggleInput()
        }
        Divider()
        if(datePickerState.showCalendarInput) {
            CalendarInput(
                datePickerState = datePickerState,
                onDateChanged = onDateChanged
            )
        }
        else {
            DateInputPicker(
                date = datePickerState.selectedDate,
                minDate = datePickerState.minDate,
                maxDate = datePickerState.maxDate,
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