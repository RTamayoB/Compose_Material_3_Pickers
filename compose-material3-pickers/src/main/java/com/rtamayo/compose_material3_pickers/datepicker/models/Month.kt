package com.rtamayo.compose_material3_pickers.datepicker.models

import java.time.DayOfWeek
import java.time.Month

data class Month(
    val monthName: Month,
    val year: Int,
    val firstDayOfTheWeek: DayOfWeek,
    var days: List<Day>? = null,
) {


    override fun toString(): String {
        return "${monthName.name} - ${days?.size} "
    }
}