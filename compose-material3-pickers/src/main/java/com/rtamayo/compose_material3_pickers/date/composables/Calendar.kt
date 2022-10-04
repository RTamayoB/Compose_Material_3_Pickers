package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
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
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.formatDate
import com.rtamayo.compose_material3_pickers.date.utils.DatePickerState
import com.rtamayo.compose_material3_pickers.date.utils.DateRangePickerState
import com.rtamayo.compose_material3_pickers.date.utils.DateRangePickerUiState
import java.time.LocalDate
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.getCalendarListIndex
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun CalendarPager(
    datePickerState: DatePickerState,
    onDateChanged: (LocalDate) -> Unit
) {
    Column {
        WeekLabels()
        HorizontalPager(
            count = datePickerState.monthList.size,
            state = datePickerState.pagerState
        ) { page ->

            val month = datePickerState.monthList[page]
            val weeks = month.weeks
            for (week in weeks) {
                for (day in week.days) {
                    day.isSelected = false
                    if (day.date == datePickerState.selectedDate) {
                        day.isSelected = true
                    }
                    if (day.date == LocalDate.now()) {
                        day.isCurrentDay = true
                    }
                    if (day.date.isBefore(datePickerState.minDate) || day.date.isAfter(datePickerState.maxDate)) {
                        day.isInDateRange = false
                    }
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
    onDateChanged: (startDate: LocalDate) -> Unit,
    dateRangePickerState: DateRangePickerState,
) {
    val state = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    var selectedItemIndex = 0

    var start by remember { mutableStateOf(startDate) }
    var end by remember { mutableStateOf(endDate) }
    var startClicked by remember { mutableStateOf(false) }
    var endClicked by remember { mutableStateOf(false) }

    val dateRangePickerUiState = dateRangePickerState.dateRangePickerUiState.value
    val numberSelectedDays = dateRangePickerUiState.numberSelectedDays.toInt()

    val selectedAnimationPercentage = remember(numberSelectedDays) {
        Animatable(0f)
    }

    LaunchedEffect(numberSelectedDays) {
        if (dateRangePickerUiState.hasSelectedDates) {

            val animationSpec: TweenSpec<Float> = tween(
                durationMillis =
                (numberSelectedDays.coerceAtLeast(0) * DURATION_MILLIS_PER_DAY)
                    .coerceAtMost(2000),
                easing = EaseOutQuart
            )
            selectedAnimationPercentage.animateTo(
                targetValue = 1f,
                animationSpec = animationSpec
            )
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
    ) {

        selectedItemIndex = getCalendarListIndex(monthList, startDate)

        for (month in monthList) {
            item {
                Text(
                    text = formatDate(LocalDate.of(month.yearMonth.year, month.yearMonth.month, 1), "MMMM yyyy"),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle.Default.copy(fontWeight = FontWeight.SemiBold)
                )
            }
            items(month.weeks) { week ->
                val beginningWeek = week.yearMonth.atDay(1).plusWeeks(week.number.toLong())
                val currentDay = beginningWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

                if (dateRangePickerUiState.hasSelectedPeriodOverlap(currentDay, currentDay.plusDays(6))) {
                    WeekSelected(
                        state = dateRangePickerUiState,
                        currentWeekStart = currentDay,
                        widthPerDay = 48.dp,
                        week = week,
                        selectedPercentageTotalProvider = { selectedAnimationPercentage.value }
                    )
                }
                Week(
                    week,
                    startDate,
                    endDate,
                    onDateChanged = onDateChanged,
                    dateRangePickerUiState
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
    val dayOfWeek = month.weeks.first().days.first().date.dayOfWeek.value
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
        for (week in month.weeks) {
            items(week.days) { day ->
                Day(
                    day = day,
                    onDateSelected = onDateChanged
                )
            }
        }
    }
}