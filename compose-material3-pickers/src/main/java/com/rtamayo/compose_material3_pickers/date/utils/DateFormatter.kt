package com.rtamayo.compose_material3_pickers.date.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.rtamayo.compose_material3_pickers.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatter {

    @Composable
    fun format(date: LocalDate, pattern: String): String {
        val locale = LocalContext.current.resources.configuration.locales[0]
        val formatter = DateTimeFormatter.ofPattern(pattern, locale)
        return date.format(formatter)
    }
}