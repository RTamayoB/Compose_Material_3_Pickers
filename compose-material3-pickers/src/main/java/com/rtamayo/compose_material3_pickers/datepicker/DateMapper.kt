package com.rtamayo.compose_material3_pickers.datepicker

import com.rtamayo.compose_material3_pickers.datepicker.models.Month
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.LongStream
import java.util.stream.Stream

object DateMapper {

    fun getDateRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {

        val size = startDate.until(endDate, ChronoUnit.DAYS)

        return LongStream.rangeClosed(0, size)
            .mapToObj(startDate::plusDays)
            .collect(Collectors.toList())
    }

    // TODO: Finish method
    fun getMonthList(calendarRange: List<LocalDate>): List<Month> {

        val monthList = listOf<Month>()

        for (date in calendarRange) {
            
        }

        return emptyList()
    }
}