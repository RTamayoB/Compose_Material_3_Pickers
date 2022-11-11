package com.rtamayo.compose_material3_pickers.time

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.R
import java.time.LocalTime

@Composable
fun TimePicker(
    time: LocalTime = LocalTime.now(),
    onTimeSelected: (time: LocalTime) -> Unit,
    onDismissRequest: () -> Unit,
) {
    PickerDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Select Time")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(time)
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
        content = {
            TimePickerContent()
        }
    )
}

@Composable
fun TimePickerContent() {
}