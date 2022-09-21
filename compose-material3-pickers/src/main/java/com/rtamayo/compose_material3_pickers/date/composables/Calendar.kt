package com.rtamayo.compose_material3_pickers.date.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.rtamayo.compose_material3_pickers.date.models.Month
import java.time.LocalDate
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.format
import com.rtamayo.compose_material3_pickers.date.utils.DateMapper.getCalendarListIndex
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarPager(
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

    var start by remember { mutableStateOf(startDate) }
    var end by remember { mutableStateOf(endDate) }
    var startClicked by remember { mutableStateOf(false) }
    var endClicked by remember { mutableStateOf(false) }

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
                        if (!startClicked) {
                            start = it
                            startClicked = true
                        }
                        else {
                            end = it
                            endClicked = true
                        }
                        if (startClicked && endClicked) {
                            startClicked = false
                            endClicked = false

                        }
                        Log.d("Selected", it.toString())
                        onDateChanged(start, end)
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