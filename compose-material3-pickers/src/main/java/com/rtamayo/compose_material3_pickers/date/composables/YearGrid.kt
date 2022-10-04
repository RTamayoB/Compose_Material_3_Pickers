package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Month
import com.rtamayo.compose_material3_pickers.date.utils.DatePickerState
import java.time.YearMonth

@Composable
fun YearGrid(
    datePickerState: DatePickerState,
    month: Month,
    onYearSelected: (yearMonth: YearMonth) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(datePickerState.yearList) { year ->
            Year(
                month = month,
                year = year,
                onYearSelected = onYearSelected
            )
        }
    }
}