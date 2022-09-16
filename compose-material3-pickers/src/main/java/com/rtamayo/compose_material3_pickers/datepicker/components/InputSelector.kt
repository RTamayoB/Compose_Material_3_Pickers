package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.datepicker.utils.DateFormatter.formatDate
import java.time.LocalDate

@Composable
internal fun InputSelector(
    localDate: LocalDate,
    onShowCalendar: (Boolean) -> Unit
) {
    var isShowingCalendar by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatDate(localDate),
            style = Typography().headlineLarge,
        )
        Spacer(modifier = Modifier.weight(1F))
        IconButton(
            onClick = {
                isShowingCalendar = !isShowingCalendar
                onShowCalendar(isShowingCalendar)
            },
            modifier = Modifier.size(24.dp)
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
                            "Select Text Input"
                        else
                            "Select Calendar Input"
                        )
            )
        }
    }
}