package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.format
import java.time.LocalDate

@Composable
internal fun InputSelector(
    localDate: LocalDate,
    onShowCalendar: (Boolean) -> Unit
) {
    InputSelectorContent(
        dateContent = {
            Text(
                text = format(localDate, "dd MMM yyyy"),
            )
        },
        onShowCalendar = onShowCalendar,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    )
}

@Composable
internal fun InputSelector(
    startDate: LocalDate,
    endDate: LocalDate,
    onShowCalendar: (Boolean) -> Unit
) {
    InputSelectorContent(
        dateContent = {
            Text(
                text = "${format(startDate, "MMM dd")} - ${format(endDate, "MMM dd")}",
            )
        },
        onShowCalendar = onShowCalendar,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp, end = 12.dp, bottom = 24.dp)
    )
}

@Composable
private fun InputSelectorContent(
    dateContent: (@Composable () -> Unit),
    onShowCalendar: (Boolean) -> Unit,
    modifier: Modifier
) {
    var isShowingCalendar by remember { mutableStateOf(true) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = MaterialTheme.typography.headlineLarge
        ProvideTextStyle(value = textStyle) {
            dateContent()
        }
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