package com.rtamayo.compose_material3_pickers.date.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.rtamayo.compose_material3_pickers.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatter {

    @Composable
    fun locale(): Locale = LocalContext.current.resources.configuration.locales[0]

    @Composable
    fun formatDate(date: LocalDate, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale())
        return date.format(formatter)
    }

    @Composable
    fun formatYearMonth(yearMonth: YearMonth, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale())
        return yearMonth.format(formatter)
    }

    @Composable
    fun formatDayOfWeek(dayOfWeek: DayOfWeek, pattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(pattern, locale())
        return dayOfWeek.name.format(pattern, locale())
    }
}