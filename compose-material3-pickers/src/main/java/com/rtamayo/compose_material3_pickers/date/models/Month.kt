package com.rtamayo.compose_material3_pickers.date.models

import java.time.DayOfWeek
import java.time.Month

data class Month(
    val month: Month,
    val year: Int,
    val firstDayOfTheWeek: DayOfWeek,
    var days: List<Day> = emptyList(),
) {


    override fun toString(): String {
        return "${month.name} - ${days?.size} "
    }
}