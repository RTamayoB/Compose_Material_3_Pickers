package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rtamayo.compose_material3_pickers.date.models.Month
import com.rtamayo.compose_material3_pickers.date.range.DateRangePickerState
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.formatYearMonth
import com.rtamayo.compose_material3_pickers.date.simple.DatePickerState
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarInput(
    datePickerState: DatePickerState,
    onDateChanged: (LocalDate) -> Unit,
) {
    Column {
        val scope = rememberCoroutineScope()

        LaunchedEffect(true) {
            scope.launch {
                datePickerState.goToCurrentMonth()
            }
        }

        val currentMonth = datePickerState.monthList[datePickerState.pagerState.currentPage]
        CalendarTopBar(
            showYearSelector = datePickerState.showYearSelector,
            month = currentMonth,
            onYearClick = {
                datePickerState.toggleYearSelector()
            },
            onPreviousMonth = {
                scope.launch {
                    datePickerState.previousMonth()
                }
            },
            onNextMonth = {
                scope.launch {
                    datePickerState.nextMonth()
                }
            }
        )
        if (datePickerState.showYearSelector) {
            YearGrid(
                datePickerState = datePickerState,
                month = currentMonth,
                onYearSelected = { yearMonth ->
                    scope.launch {
                        datePickerState.goToMonth(yearMonth)
                    }
                }
            )
        } else {
            CalendarPager(
                datePickerState = datePickerState,
                onDateChanged = onDateChanged
            )
        }
    }
}

@Composable
fun CalendarRangeInput(
    startDate: LocalDate,
    monthList: List<Month>,
    onDateChanged: (startDate: LocalDate) -> Unit,
    dateRangePickerState: DateRangePickerState
) {
    Column {
        WeekLabels()
        CalendarList(
            startDate = startDate,
            monthList = monthList,
            onDateChanged = onDateChanged,
            dateRangePickerState = dateRangePickerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarTopBar(
    showYearSelector: Boolean,
    month: Month,
    onYearClick: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        AssistChip(
            onClick = onYearClick,
            label = {
                Text(text = formatYearMonth(month.yearMonth, "MMMM yyyy"))
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = ""
                )
            },
            shape = CircleShape,
            border = null,
        )
        Spacer(modifier = Modifier.weight(1F))
        if (!showYearSelector) {
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = ""
                )
            }
            IconButton(onClick = onNextMonth) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}