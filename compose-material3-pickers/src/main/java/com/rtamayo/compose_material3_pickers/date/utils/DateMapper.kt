package com.rtamayo.compose_material3_pickers.date.utils

import com.rtamayo.compose_material3_pickers.date.models.Day
import com.rtamayo.compose_material3_pickers.date.models.Month as ModelMonth
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.ChronoUnit

object DateMapper {

    fun getMonthList(startDate: LocalDate, endDate: LocalDate): List<ModelMonth> {
        /*
        val yearMin = (100.0 * floor(abs(startDate.year / 100.0))).toInt()
        val yearMax = (100.0 * ceil(abs(endDate.year / 100.0))).toInt()
        val min = LocalDate.now().withYear(yearMin).withDayOfYear(1)
        val max = LocalDate.now().withYear(yearMax).withDayOfYear(endDate.lengthOfYear())
        */
        val calendarRange = DateIterator(startDate, endDate).toList()
        val monthList = mutableListOf<ModelMonth>()
        val days = mutableListOf<Day>()

        val firstDay = calendarRange.first()

        var month = ModelMonth(
            firstDay.month,
            firstDay.year,
            firstDay.withDayOfMonth(1).dayOfWeek,
        )
        var currentMonth: Month? = firstDay.month
        if(firstDay.dayOfMonth != 1) {
            for (i in 1 until firstDay.dayOfMonth) {
                days.add(Day(LocalDate.of(firstDay.year, firstDay.monthValue, i)))
            }
        }

        for (date in calendarRange) {
            if(date.month != currentMonth) {
                val newMonth = ModelMonth(
                    date.month,
                    date.year,
                    date.dayOfWeek,
                )
                month.days = days.toList()
                monthList.add(month)
                days.clear()
                month = newMonth
                currentMonth = date.month
            }
            days.add(Day(date))
        }

        val isLeap = Year.isLeap(month.year.toLong())
        val monthLength = month.month.length(isLeap)
        if(monthLength - days.size > 1) {
            for (i in days.size until monthLength) {
                days.add(Day(LocalDate.of(month.year, month.month.value, i + 1)))
            }
            month.days = days
            monthList.add(month)

        }

        return monthList
    }

    fun getYearList(monthList: List<ModelMonth>) : List<Int> {
        val yearList = mutableListOf<Int>()
        var year = monthList.first().year
        yearList.add(year)
        for (month in monthList) {
            if(month.year != year) {
                yearList.add(month.year)
                year = month.year
            }
        }

        return  yearList.toList()
    }

    fun getMonth(monthList: List<ModelMonth>, currentYear: Int, month: ModelMonth, year: Int): ModelMonth {

        var selectedMonth = monthList.first()
        val list = if (currentYear <= year) {
            monthList
        } else {
            monthList.reversed()
        }
        for (m in list) {
            if(m.month == month.month && m.year == year) {
                selectedMonth = m
                break
            }
            selectedMonth = m
        }

        return selectedMonth
    }

    fun getPage(monthList: List<ModelMonth>, date: LocalDate): Int {

        for (month in monthList) {
            for (day in month.days) {
                if (day.date == date) {
                    return monthList.indexOf(month)
                }
            }
        }

        return 0
    }

    fun getCalendarListIndex(monthList: List<ModelMonth>, date: LocalDate): Int {
        var dayOfWeekOffset = 0
        var currentMonth: LocalDate
        for (month in monthList) {
            currentMonth = LocalDate.of(month.year, month.month, 1)
            if (currentMonth.isBefore(date)) {
                dayOfWeekOffset += month.firstDayOfTheWeek.value
            } else {
                break
            }
        }
        val initialDate = LocalDate.of(monthList.first().year, monthList.first().month, 1)
        val daysBetween = ChronoUnit.DAYS.between(initialDate, date) - date.dayOfMonth
        val monthsBetween = ChronoUnit.MONTHS.between(initialDate, date) - 1
        return (daysBetween.toInt() + monthsBetween.toInt() + dayOfWeekOffset) - date.dayOfWeek.value
    }
}