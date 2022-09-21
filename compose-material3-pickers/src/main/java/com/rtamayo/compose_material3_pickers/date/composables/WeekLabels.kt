package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.R

@Composable
internal fun WeekLabels() {

    val weekLabels = stringArrayResource(id = R.array.week_labels)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            items = weekLabels
        ) {
            Box(
                modifier = Modifier.size(49.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        }
    }
}