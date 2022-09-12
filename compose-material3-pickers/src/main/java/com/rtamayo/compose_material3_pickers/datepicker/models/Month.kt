package com.rtamayo.compose_material3_pickers.datepicker.models

import java.time.DayOfWeek
import java.time.Month

data class Month(
    val monthName: Month,
    val firstDayOfTheWeek: DayOfWeek,
    val size: Int
)