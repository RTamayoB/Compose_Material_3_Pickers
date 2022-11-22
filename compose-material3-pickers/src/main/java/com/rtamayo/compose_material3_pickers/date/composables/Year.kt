package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Month
import java.time.YearMonth

//TODO Fix selected padding
@Composable
internal fun Year(
    month: Month,
    year: Int,
    onYearSelected:(yearMonth: YearMonth) -> Unit,
) {
    var yearModifier = Modifier
        .size(72.dp, 36.dp)
        .clip(CircleShape)

    if(month.yearMonth.year == year) {
        yearModifier = yearModifier
            .background(Color.Blue)
    }
    Box(
        modifier = Modifier.size(88.dp, 52.dp),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = { onYearSelected(YearMonth.of(year, month.yearMonth.month)) },
            modifier = yearModifier
        ) {
            Text(
                text = year.toString(),
                textAlign = TextAlign.Center,
                color = if (month.yearMonth.year == year) Color.White else Color.Black
            )
        }
    }
}