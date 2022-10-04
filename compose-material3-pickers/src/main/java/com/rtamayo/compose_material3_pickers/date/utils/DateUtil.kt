package com.rtamayo.compose_material3_pickers.date.utils

import com.rtamayo.compose_material3_pickers.date.models.Day
import com.rtamayo.compose_material3_pickers.date.models.Week
import java.time.*
import com.rtamayo.compose_material3_pickers.date.models.Month as ModelMonth
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import kotlin.math.abs
import kotlin.math.ceil

object DateUtil {

    /*
    val yearMin = (100.0 * floor(abs(startDate.year / 100.0))).toInt()
    val yearMax = (100.0 * ceil(abs(endDate.year / 100.0))).toInt()
    val min = LocalDate.now().withYear(yearMin).withDayOfYear(1)
    val max = LocalDate.now().withYear(yearMax).withDayOfYear(endDate.lengthOfYear())
    */

    fun getYearList(monthList: List<ModelMonth>) : List<Int> {
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

    fun getMonth(monthList: List<ModelMonth>, currentYear: Int, yearMonth: YearMonth): ModelMonth {

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

    fun getMonthList(startDate: LocalDate, endDate: LocalDate): List<ModelMonth> {

        val tempListMonths = mutableListOf<ModelMonth>()
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
            val month = ModelMonth(startYearMonth, listWeekItems)
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

    fun getPage(monthList: List<ModelMonth>, date: LocalDate): Int {
        for (month in monthList) {
            if(month.yearMonth.year == date.year && month.yearMonth.month == date.month) {
                return monthList.indexOf(month)
            }
        }
        return 0
    }

    fun getCalendarListIndex(monthList: List<ModelMonth>, date: LocalDate): Int {
        var dayOfWeekOffset = 0
        var currentMonth: LocalDate
        for (month in monthList) {
            currentMonth = LocalDate.of(month.yearMonth.year, month.yearMonth.month, 1)
            if (currentMonth.isBefore(date)) {
                dayOfWeekOffset += month.weeks.first().days.first().date.dayOfWeek.value
            } else {
                break
            }
        }
        val initialDate = LocalDate.of(monthList.first().yearMonth.year, monthList.first().yearMonth.month, 1)
        val daysBetween = ChronoUnit.DAYS.between(initialDate, date) - date.dayOfMonth
        val monthsBetween = ChronoUnit.MONTHS.between(initialDate, date) - 1
        return (daysBetween.toInt() + monthsBetween.toInt() + dayOfWeekOffset) - date.dayOfWeek.value
    }

    private val yearMax = (100.0 * ceil(abs(LocalDate.now().year / 100.0))).toInt()

    //Use Ceil/Floor functions to return minus 100 years and plus 100 years
    val MIN_DATE: LocalDate = LocalDate.now()
    val MAX_DATE: LocalDate = LocalDate.now().withYear(yearMax).withDayOfYear(LocalDate.now().lengthOfYear())
}