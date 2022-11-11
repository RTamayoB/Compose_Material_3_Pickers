package com.rtamayo.compose_material3_pickers.date.range

import androidx.compose.runtime.*
import com.rtamayo.compose_material3_pickers.date.AnimationDirection
import com.rtamayo.compose_material3_pickers.date.DateRangePickerUiState
import com.rtamayo.compose_material3_pickers.date.models.Month
import com.rtamayo.compose_material3_pickers.date.models.Week
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MAX_DATE
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MIN_DATE
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth
import java.time.temporal.WeekFields

class DateRangePickerState(
    val minDate: LocalDate,
    val maxDate: LocalDate
) {

    private val CALENDAR_STARTS_ON = WeekFields.ISO

    val dateRangePickerUiState = mutableStateOf(DateRangePickerUiState())
    private val listMonths: List<Month>

    var selectedStartDate: LocalDate by mutableStateOf(minDate)
        private set

    var selectedEndDate: LocalDate by mutableStateOf(maxDate)
        private set

    var showCalendarInput: Boolean by mutableStateOf(true)
        private set

    val dateFormatted: String
        @Composable get() = "${DateFormatter.formatDate(selectedStartDate, "MMM dd")} - ${DateFormatter.formatDate(selectedEndDate, "MMM dd")}"

    private val periodBetweenCalendarStartEnd: Period = Period.between(
        minDate,
        maxDate
    )

    fun toggleInput() {
        showCalendarInput = !showCalendarInput
    }

    fun setStartDate(startDate: LocalDate) {
        selectedStartDate = startDate
    }

    fun setEndDate(endDate: LocalDate) {
        selectedEndDate = endDate
    }

    init {
        val tempListMonths = mutableListOf<Month>()
        var startYearMonth = YearMonth.from(minDate)
        for (numberMonth in 0..periodBetweenCalendarStartEnd.toTotalMonths()) {
            val numberWeeks = startYearMonth.getNumberWeeks()
            val listWeekItems = mutableListOf<Week>()
            for (week in 0 until numberWeeks) {
                listWeekItems.add(
                    Week(
                        number = week,
                        yearMonth = startYearMonth
                    )
                )
            }
            val month = Month(startYearMonth, listWeekItems)
            tempListMonths.add(month)
            startYearMonth = startYearMonth.plusMonths(1)
        }
        listMonths = tempListMonths.toList()
    }

    fun setSelectedDay(newDate: LocalDate) {
        dateRangePickerUiState.value = updateSelectedDay(newDate)
    }

    private fun updateSelectedDay(newDate: LocalDate): DateRangePickerUiState {
        val currentState = dateRangePickerUiState.value
        val selectedStartDate = currentState.selectedStartDate
        val selectedEndDate = currentState.selectedEndDate

        return when {
            selectedStartDate == null && selectedEndDate == null -> {
                currentState.setDates(newDate, null)
            }
            selectedStartDate != null && selectedEndDate != null -> {
                val animationDirection = if (newDate.isBefore(selectedStartDate)) {
                    AnimationDirection.BACKWARDS
                } else {
                    AnimationDirection.FORWARDS
                }
                this.dateRangePickerUiState.value = currentState.copy(
                    selectedStartDate = null,
                    selectedEndDate = null,
                    animateDirection = animationDirection
                )
                updateSelectedDay(newDate = newDate)
            }
            selectedStartDate == null -> {
                if (newDate.isBefore(selectedEndDate)) {
                    currentState.copy(animateDirection = AnimationDirection.BACKWARDS)
                        .setDates(newDate, selectedEndDate)
                } else if (newDate.isAfter(selectedEndDate)) {
                    currentState.copy(animateDirection = AnimationDirection.FORWARDS)
                        .setDates(selectedEndDate, newDate)
                } else {
                    currentState
                }
            }
            else -> {
                if (newDate.isBefore(selectedStartDate)) {
                    currentState.copy(animateDirection = AnimationDirection.BACKWARDS)
                        .setDates(newDate, selectedStartDate)
                } else if (newDate.isAfter(selectedStartDate)) {
                    currentState.copy(animateDirection = AnimationDirection.FORWARDS)
                        .setDates(selectedStartDate, newDate)
                } else {
                    currentState
                }
            }
        }
    }

    companion object {
        const val DAYS_IN_WEEK = 7
    }

    fun YearMonth.getNumberWeeks(weekFields: WeekFields = CALENDAR_STARTS_ON): Int {
        val firstWeekNumber = this.atDay(1)[weekFields.weekOfMonth()]
        val lastWeekNumber = this.atEndOfMonth()[weekFields.weekOfMonth()]
        return lastWeekNumber - firstWeekNumber + 1 // Both weeks inclusive
    }
}

@Composable
fun rememberDateRangePickerState(
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE
) = remember(minDate, maxDate) {
    DateRangePickerState(minDate, maxDate)
}