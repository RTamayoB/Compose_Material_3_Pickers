package com.rtamayo.compose_material3_pickers.datepicker

import android.util.Log
import com.rtamayo.compose_material3_pickers.datepicker.models.Day
import com.rtamayo.compose_material3_pickers.datepicker.models.Month as ModelMonth
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.ChronoUnit

object DateMapper {


    fun getDateRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val daysList = mutableListOf<LocalDate>()
        var range = ChronoUnit.DAYS.between(startDate, startDate.plusDays(300))
        for (i in 0 until range) {
            daysList.add(startDate.plusDays(i + 1))
        }
        return daysList
    }

    fun getMonthList(calendarRange: List<LocalDate>): List<ModelMonth> {

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
        val monthLength = month.monthName.length(isLeap)
        if(monthLength - days.size > 1) {
            for (i in days.size until monthLength) {
                days.add(Day(LocalDate.of(month.year, month.monthName.value, i + 1)))
            }
            month.days = days
            monthList.add(month)

        }

        return monthList
    }
}