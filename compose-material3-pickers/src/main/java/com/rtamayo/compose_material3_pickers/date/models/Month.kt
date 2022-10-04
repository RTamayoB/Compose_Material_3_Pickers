package com.rtamayo.compose_material3_pickers.date.models

import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth

data class Month(
    val yearMonth: YearMonth,
    var weeks: List<Week>
) {

    override fun toString(): String {
        return yearMonth.toString()
    }
}