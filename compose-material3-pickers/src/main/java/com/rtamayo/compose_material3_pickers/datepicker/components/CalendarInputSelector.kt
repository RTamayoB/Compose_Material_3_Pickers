package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate

@Composable
internal fun CalendarInputSelector(
    localDate: LocalDate,
    onShowCalendar: (Boolean) -> Unit
) {
    var isShowingCalendar by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = localDate.toString(),
            style = Typography().headlineLarge
        )
        Spacer(modifier = Modifier.weight(1F))
        IconButton(
            onClick = {
                isShowingCalendar = !isShowingCalendar
                onShowCalendar(isShowingCalendar)
            }
        ) {
            Icon(
                imageVector = (
                        if (isShowingCalendar)
                            Icons.Default.Edit
                        else
                            Icons.Default.DateRange
                        ),
                contentDescription = (
                        if (isShowingCalendar)
                            "Switch to Text Input"
                        else
                            "Switch to Calendar Input"
                        )
            )
        }
    }
}