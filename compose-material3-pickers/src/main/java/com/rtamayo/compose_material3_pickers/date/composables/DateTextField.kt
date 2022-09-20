package com.rtamayo.compose_material3_pickers.date.composables

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import java.time.LocalDate
import java.time.format.DateTimeParseException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DateTextField(
    date: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    format: String,
    trailingIcon: @Composable () -> Unit,
    isError: Boolean
) {
    OutlinedTextField(
        value = date,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(
                text = "Date",
                textAlign = TextAlign.Center
            )
        },
        placeholder = {
            Text(text = format)
        },
        trailingIcon = trailingIcon,
        isError = isError
    )
}