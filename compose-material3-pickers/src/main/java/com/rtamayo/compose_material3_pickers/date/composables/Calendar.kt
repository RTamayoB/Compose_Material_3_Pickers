package com.rtamayo.compose_material3_pickers.datepicker.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.rtamayo.compose_material3_pickers.date.models.Month
import java.time.LocalDate
import com.rtamayo.compose_material3_pickers.date.composables.WeekLabels
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.format
import com.rtamayo.compose_material3_pickers.date.utils.DateMapper.getCalendarListIndex
import kotlinx.coroutines.launch
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun Calendar(
    date: LocalDate,
    monthList: List<Month>,
    pagerState: PagerState,
    onDateChanged: (LocalDate) -> Unit
) {
    Column {
        WeekLabels()
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
fun CalendarList(
    startDate: LocalDate,
    endDate: LocalDate,
    monthList: List<Month>,
    onDateChanged: (startDate: LocalDate, endDate: LocalDate) -> Unit
) {
    val state = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    var selectedItemIndex = 0

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        state = state,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {

        selectedItemIndex = getCalendarListIndex(monthList, startDate)

        for (month in monthList) {
            val daysOfTheWeek = month.firstDayOfTheWeek.value
            item(span = { GridItemSpan(7) }) {
                Text(
                    text = format(LocalDate.of(month.year, month.month, 1), "MMMM yyyy"),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle.Default.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            items(daysOfTheWeek, span = { GridItemSpan(1)}) {
                Box(modifier = Modifier)
            }
            items(month.days) { day ->
                if (day.date == startDate) {
                    day.isSelected = true
                }
                if (day.date == LocalDate.now()) {
                    day.isCurrentDay = true
                }
                Day(
                    day = day,
                    onDateSelected = {
                        onDateChanged(it, it.plusDays(5))
                    }
                )
            }
        }
    }
    LaunchedEffect(key1 = true) {
        scope.launch {
            state.scrollToItem(selectedItemIndex)
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
            .fillMaxHeight(),
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

@Composable
internal fun CalendarRow(
    month: Month,
    onDateChanged: (LocalDate) -> Unit,
) {
    val dayOfWeek = month.firstDayOfTheWeek.value
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        for (i in 0 until dayOfWeek) {
            Box(modifier = Modifier.border(1.dp, Color.Blue))
        }
        for (day in month.days) {
            Day(
                day = day,
                onDateSelected = onDateChanged
            )
        }
    }
}