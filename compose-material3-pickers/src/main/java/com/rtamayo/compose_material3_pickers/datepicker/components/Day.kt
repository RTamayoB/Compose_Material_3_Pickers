package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.datepicker.models.Day
import java.time.LocalDate

@Composable
fun Day(
    day: Day,
    onDateSelected: (LocalDate) -> Unit,
) {
    var dayModifier = Modifier
        .clip(CircleShape)
        .size(36.dp)
        .aspectRatio(1F)

    if (day.isCurrentDay) {
        dayModifier = dayModifier
            .border(1.dp, Color.Blue, CircleShape)
    }

    if(day.isSelected) {
        dayModifier = dayModifier
            .background(Color.Blue)
    }

    IconButton(
        onClick = {
            onDateSelected(day.date)
        },
        enabled = day.isInDateRange
    ) {
        Box(
            modifier = dayModifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                color = if (day.isSelected) Color.White else Color.Black
            )
        }
    }
}