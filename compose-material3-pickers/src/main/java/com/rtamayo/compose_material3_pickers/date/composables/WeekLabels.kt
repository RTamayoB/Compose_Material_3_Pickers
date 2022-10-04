package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.formatDayOfWeek
import java.time.DayOfWeek

@Composable
internal fun WeekLabels() {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(DayOfWeek.values()) { dayOfWeek ->
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = formatDayOfWeek(dayOfWeek, "E").take(1),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        }
    }
}