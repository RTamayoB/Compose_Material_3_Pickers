package com.rtamayo.compose_material3_pickers.date.simple

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.formatDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// TODO: Make Ok button disable on errors, Create UiState class
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputPicker(
    date: LocalDate,
    minDate: LocalDate,
    maxDate: LocalDate,
    onDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    format: String = "dd/MM/yy",
) {
    val locale = LocalContext.current.resources.configuration.locales
    val formatter = DateTimeFormatter.ofPattern(format, locale[0])
    var dateFormatted by remember { mutableStateOf(date.format(formatter)) }
    var invalidFormat by remember { mutableStateOf(false) }
    var outOfRange by remember { mutableStateOf(false) }


    var newDate by remember { mutableStateOf(date) }
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = dateFormatted,
            onValueChange = {
                dateFormatted = it
                try {
                    newDate = LocalDate.parse(dateFormatted, formatter)
                    onDateChange(newDate)
                    outOfRange = !(newDate.isAfter(minDate) && newDate.isBefore(maxDate))
                    invalidFormat = false
                } catch (p: DateTimeParseException) {
                    invalidFormat = true
                }
            },
            label = {
                Text(
                    text = "Date",
                    textAlign = TextAlign.Center
                )
            },
            placeholder = {
                Text(text = format)
            },
            trailingIcon = {
                if (invalidFormat || outOfRange) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            isError = invalidFormat || outOfRange
        )
        Column(
            Modifier.padding(start = 16.dp, top = 2.dp)
        ) {
            if (invalidFormat) {
                ErrorText(value = "Invalid Format")
                ErrorText(value = "Use: $format")
                ErrorText(value = "Example: ${LocalDate.now().format(formatter)}")
            }
            if (outOfRange) {
                ErrorText(value = "Out of range: ${formatDate(newDate, "dd MMM yyy")}")
            }
        }
    }
}

@Composable
internal fun ErrorText(
    value: String
) {

    Text(
        text = value,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Start,
        style = Typography().bodySmall
    )
}

data class InputError(val message: String)