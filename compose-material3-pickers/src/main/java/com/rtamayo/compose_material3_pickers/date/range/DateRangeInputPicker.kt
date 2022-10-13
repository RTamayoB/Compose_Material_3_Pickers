package com.rtamayo.compose_material3_pickers.date

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.composables.DateTextField
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// TODO: Make Ok button disable on errors, Create UiState class
@Composable
fun DateRangeInputPicker(
    startDate: LocalDate,
    endDate: LocalDate,
    minDate: LocalDate,
    maxDate: LocalDate,
    onDateChange: (startDate: LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    format: String = "dd/MM/yy",
) {
    val locale = LocalContext.current.resources.configuration.locales
    val formatter = DateTimeFormatter.ofPattern(format, locale[0])
    var startDateFormatted by remember { mutableStateOf(startDate.format(formatter)) }
    var endDateFormatted by remember { mutableStateOf(endDate.format(formatter)) }
    var invalidFormat by remember { mutableStateOf(false) }
    var outOfRange by remember { mutableStateOf(false) }


    var newStartDate by remember { mutableStateOf(startDate) }
    var newEndDate by remember { mutableStateOf(startDate) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DateTextField(
            date = startDateFormatted,
            format = format,
            onValueChange = {
                startDateFormatted = it
                try {
                    newStartDate = LocalDate.parse(startDateFormatted, formatter)
                    onDateChange(newStartDate)
                    outOfRange = !(newStartDate.isAfter(minDate) && newStartDate.isBefore(maxDate))
                    invalidFormat = false
                } catch (p: DateTimeParseException) {
                    invalidFormat = true
                }
            },
            modifier = Modifier.weight(.5F),
            trailingIcon = {
                if (invalidFormat || outOfRange) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = false
        )
        DateTextField(
            date = endDateFormatted,
            format = format,
            onValueChange = {
                endDateFormatted = it
                try {
                    newEndDate = LocalDate.parse(endDateFormatted, formatter)
                    onDateChange(newStartDate)
                    outOfRange = !(newEndDate.isAfter(minDate) && newEndDate.isBefore(maxDate))
                    invalidFormat = false
                } catch (p: DateTimeParseException) {
                    invalidFormat = true
                }
            },
            modifier = Modifier.weight(.5F),
            trailingIcon = {
                if (invalidFormat || outOfRange) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = false
        )
    }
}