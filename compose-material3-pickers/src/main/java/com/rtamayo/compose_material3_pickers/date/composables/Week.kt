package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Week
import com.rtamayo.compose_material3_pickers.date.utils.DateRangePickerUiState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Composable
fun Week(
    week: Week,
    startDate: LocalDate,
    endDate: LocalDate,
    onDateChanged: (date: LocalDate) -> Unit,
    dateRangePickerUiState: DateRangePickerUiState
) {
    val beginningWeek = week.yearMonth.atDay(1).plusWeeks(week.number.toLong())
    var currentDay = beginningWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    Box {
        Row(modifier = Modifier
                .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)) {
            for (i in 0..6) {
                if (currentDay.month == week.yearMonth.month) {
                    Day(
                        day = currentDay,
                        dateRangePickerUiState = dateRangePickerUiState,
                        onDayClicked = onDateChanged,
                        month = week.yearMonth
                    )
                } else {
                    Box(modifier = Modifier.size(48.dp))
                }
                currentDay = currentDay.plusDays(1)
            }
        }
    }
}