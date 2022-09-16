package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.datepicker.models.Month
import com.rtamayo.compose_material3_pickers.datepicker.utils.DateMapper

@Composable
fun YearGrid(
    month: Month,
    monthList: List<Month>,
    onYearSelected: (month: Month, year: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        //TODO: Get list of Years
        val yearList = DateMapper.getYearList(monthList)
        items(yearList) { year ->
            Year(
                month,
                year = year,
                onYearSelected = onYearSelected
            )
        }
    }
}