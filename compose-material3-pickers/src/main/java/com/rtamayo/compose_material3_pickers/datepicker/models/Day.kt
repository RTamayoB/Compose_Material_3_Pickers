package com.rtamayo.compose_material3_pickers.datepicker.models

import java.time.LocalDate

data class Day(
    val date: LocalDate,
    var isInDateRange: Boolean = true,
    var isCurrentDay: Boolean = false,
    var isSelected: Boolean = false
) {
    override fun toString(): String {
        return date.dayOfMonth.toString()
    }
}