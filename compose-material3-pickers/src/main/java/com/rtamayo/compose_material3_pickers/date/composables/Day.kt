package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Day
import java.time.LocalDate

@Composable
internal fun Day(
    day: Day,
    onDateSelected: (LocalDate) -> Unit,
) {
    var dayModifier = Modifier
        .clip(CircleShape)
        .size(40.dp)
        .aspectRatio(1F)

    if (day.isCurrentDay) {
        dayModifier = dayModifier
            .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
    }

    if(day.isSelected) {
        dayModifier = dayModifier
            .background(MaterialTheme.colorScheme.primary)
    }

    val color =
        if (!day.isInDateRange)
            Color.Gray
        else if (day.isSelected)
            Color.White
        else
            Color.Black

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
                color = color
            )
        }
    }
}