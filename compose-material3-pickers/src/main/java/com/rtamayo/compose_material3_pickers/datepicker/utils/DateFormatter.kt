package com.rtamayo.compose_material3_pickers.datepicker.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rtamayo.compose_material3_pickers.R
import java.time.LocalDate

object DateFormatter {

    @Composable
    fun formatDate(localDate: LocalDate): String {
        val day = localDate.dayOfMonth.toString()
        val month = localDate.month.toString().take(3).lowercase().replaceFirstChar { it.uppercase() }
        val year = localDate.year
        return stringResource(id = R.string.date, day, month, year)
    }

    @Composable
    fun formatMonth(month: String, year: Int): String {
        val monthFormatted = month.lowercase().replaceFirstChar { it.uppercase() }

        return stringResource(id = R.string.month_with_year, monthFormatted, year)
    }
}