package com.rtamayo.compose_material3_pickers.datepicker.models

import java.time.LocalDate

data class Day(
    val date: LocalDate,
    val isInDateRange: Boolean = true,
    val isCurrentDay: Boolean = false,
    val isSelected: Boolean = false
) {
    override fun toString(): String {
        return date.dayOfMonth.toString()
    }
}