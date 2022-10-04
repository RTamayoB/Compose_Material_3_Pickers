package com.rtamayo.compose_material3_pickers.date.utils

import androidx.compose.runtime.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.rtamayo.compose_material3_pickers.date.models.Day
import com.rtamayo.compose_material3_pickers.date.models.Month
import com.rtamayo.compose_material3_pickers.date.models.Week
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.formatDate
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MAX_DATE
import com.rtamayo.compose_material3_pickers.date.utils.DateUtil.MIN_DATE
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields

@OptIn(ExperimentalPagerApi::class)
class DatePickerState(
    date: LocalDate,
    val minDate: LocalDate,
    val maxDate: LocalDate,
    val pagerState: PagerState
) {

    //Add pager management, yearselector, and stuff from date util
    val monthList = getMonthList(minDate, maxDate)
    val yearList = getYearList(monthList)

    var selectedDate: LocalDate by mutableStateOf(date)
        private set

     val dateFormatted: String
        @Composable get() = formatDate(selectedDate, "dd MMM yyyy")

    var showCalendarInput: Boolean by mutableStateOf(true)
        private set

    var showYearSelector: Boolean by mutableStateOf(false)
        private set

    private var currentPage = getPage(monthList, selectedDate)

    fun setDate(newDate: LocalDate) {
        selectedDate = newDate
    }

    fun toggleInput() {
        showCalendarInput = !showCalendarInput
    }

    fun toggleYearSelector() {
        showYearSelector = !showYearSelector
    }

    suspend fun goToCurrentMonth() {
        pagerState.scrollToPage(currentPage)
    }

    suspend fun goToMonth(yearMonth: YearMonth) {
        val currentYear = monthList[currentPage].yearMonth.year
        val selectedMonth = getMonth(monthList, currentYear, yearMonth)
        currentPage = monthList.indexOf(selectedMonth)
        pagerState.scrollToPage(currentPage)
        showYearSelector = false
    }

    suspend fun previousMonth() {
        if (currentPage > 0) {
            currentPage -= 1
            pagerState.animateScrollToPage(currentPage)
        }
    }

    suspend fun nextMonth() {
        if (currentPage < monthList.size) {
            currentPage += 1
            pagerState.animateScrollToPage(currentPage)
        }
    }

    private fun getPage(monthList: List<Month>, date: LocalDate): Int {
        for (month in monthList) {
            if(month.yearMonth.year == date.year && month.yearMonth.month == date.month) {
                return monthList.indexOf(month)
            }
        }
        return 0
    }

    private fun getMonthList(startDate: LocalDate, endDate: LocalDate): List<Month> {

        val tempListMonths = mutableListOf<Month>()
        var startYearMonth = YearMonth.from(startDate)
        val periodBetweenCalendarStartEnd = Period.between(startDate, endDate)
        for (numberMonth in 0..periodBetweenCalendarStartEnd.toTotalMonths()) {
            val numberWeeks = startYearMonth.getNumberWeeks()
            val listWeekItems = mutableListOf<Week>()
            for (numberWeek in 0 until numberWeeks) {
                val tempListDays = mutableListOf<Day>()
                val firstDayOfWeek = startYearMonth.atDay(1).plusWeeks(numberWeek.toLong())
                var currentDay = firstDayOfWeek.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                currentDay = currentDay.minusDays(1)
                for (i in 0..6) {
                    if(currentDay.month == startYearMonth.month) {
                        tempListDays.add(
                            Day(
                                date = currentDay
                            )
                        )
                    }
                    currentDay = currentDay.plusDays(1)
                }
                if (tempListDays.isNotEmpty()) {
                    listWeekItems.add(
                        Week(
                            numberWeek,
                            startYearMonth,
                            tempListDays.toList()
                        )
                    )
                }
            }
            val month = Month(startYearMonth, listWeekItems)
            tempListMonths.add(month)
            startYearMonth = startYearMonth.plusMonths(1)
        }
        return tempListMonths.toList()
    }

    private fun YearMonth.getNumberWeeks(weekFields: WeekFields = WeekFields.ISO): Int {
        val firstWeekNumber = this.atDay(1)[weekFields.weekOfMonth()]
        val lastWeekNumber = this.atEndOfMonth()[weekFields.weekOfMonth()]
        return lastWeekNumber - firstWeekNumber + 1 // Both weeks inclusive
    }

    fun getMonth(monthList: List<Month>, currentYear: Int, yearMonth: YearMonth): Month {

        var selectedMonth = monthList.first()
        val list = if (currentYear <= yearMonth.year) {
            monthList
        } else {
            monthList.reversed()
        }
        for (m in list) {
            if(m.yearMonth.month == yearMonth.month && m.yearMonth.year == yearMonth.year) {
                selectedMonth = m
                break
            }
            selectedMonth = m
        }

        return selectedMonth
    }

    fun getYearList(monthList: List<Month>) : List<Int> {
        val yearList = mutableListOf<Int>()
        var firstYear = monthList.first().yearMonth.year
        yearList.add(firstYear)
        for (month in monthList) {
            if(month.yearMonth.year != firstYear) {
                yearList.add(month.yearMonth.year)
                firstYear = month.yearMonth.year
            }
        }

        return yearList.toList()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun rememberDatePickerState(
    date: LocalDate = LocalDate.now(),
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE,
    pagerState: PagerState = rememberPagerState()
) = remember(date, minDate, maxDate, pagerState) {
    DatePickerState(date, minDate, maxDate, pagerState)
}