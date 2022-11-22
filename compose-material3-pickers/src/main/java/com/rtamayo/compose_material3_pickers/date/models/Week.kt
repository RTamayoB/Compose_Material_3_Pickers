package com.rtamayo.compose_material3_pickers.date.models

import java.time.YearMonth

data class Week(
    val number: Int,
    val yearMonth: YearMonth,
    var days: List<Day> = emptyList()
)