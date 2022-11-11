package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Day
import com.rtamayo.compose_material3_pickers.date.models.Week
import com.rtamayo.compose_material3_pickers.date.range.DateRangePickerState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun Week(
    week: Week,
    onDateChanged: (date: LocalDate) -> Unit,
    dateRangePickerState: DateRangePickerState
) {
    val beginningWeek = week.yearMonth.atDay(1).plusWeeks(week.number.toLong())
    var currentDay = beginningWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    val dateRangePickerUiState = dateRangePickerState.dateRangePickerUiState

    Box {
        Row(modifier = Modifier
                .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)) {
            for (i in 0..6) {
                if (currentDay.month == week.yearMonth.month) {
                    val day = Day(currentDay)
                    if (day.date == dateRangePickerUiState.value.selectedStartDate || day.date == dateRangePickerUiState.value.selectedEndDate)
                        day.isSelected = true
                    if (day.date.isBefore(dateRangePickerState.minDate) || day.date.isAfter(dateRangePickerState.maxDate)) {
                        day.isInDateRange = false
                    }
                    Day(
                        day = day,
                        onDateSelected = onDateChanged,
                    )
                } else {
                    Box(modifier = Modifier.size(48.dp))
                }
                currentDay = currentDay.plusDays(1)
            }
        }
    }
}