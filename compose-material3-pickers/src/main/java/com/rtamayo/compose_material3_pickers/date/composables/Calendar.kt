package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.rtamayo.compose_material3_pickers.date.models.Month
import java.time.LocalDate
import com.rtamayo.compose_material3_pickers.R

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun Calendar(
    date: LocalDate,
    monthList: List<Month>,
    pagerState: PagerState,
    onDateChanged: (LocalDate) -> Unit
) {
    val weekLabels = stringArrayResource(id = R.array.week_labels)
    Column {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(
                items = weekLabels
            ) {
                Box(
                    modifier = Modifier.size(40.dp),
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
        HorizontalPager(
            count = monthList.size,
            state = pagerState
        ) { page ->

            val month = monthList[page]
            val days = month.days
            for (day in days) {
                day.isSelected = false
                if (day.date == date) {
                    day.isSelected = true
                }
                if (day.date == LocalDate.now()) {
                    day.isCurrentDay = true
                }
            }
            CalendarGrid(
                month = month,
                onDateChanged = onDateChanged
            )
        }
    }

}

@Composable
internal fun CalendarGrid(
    month: Month,
    onDateChanged: (LocalDate) -> Unit,
) {
    val dayOfWeek = month.firstDayOfTheWeek.value
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        ,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Fixed(7),
        userScrollEnabled = false
    ) {
        items(dayOfWeek) {
            Box(modifier = Modifier)
        }
        items(month.days) { day ->
            Day(
                day = day,
                onDateSelected = onDateChanged
            )
        }
    }
}