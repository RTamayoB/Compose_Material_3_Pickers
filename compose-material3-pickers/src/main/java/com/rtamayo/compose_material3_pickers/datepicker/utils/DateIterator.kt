package com.rtamayo.compose_material3_pickers.datepicker.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Stream
import kotlin.streams.toList

class DateIterator(
    val startDate: LocalDate,
    val endDate: LocalDate
): Iterable<LocalDate> {
    override fun iterator(): Iterator<LocalDate> {
        return stream().iterator()
    }

    private fun stream(): Stream<LocalDate> {
        return Stream.iterate(startDate) { d ->
            d.plusDays(1)
        }.limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
    }

    fun toList(): List<LocalDate> = stream().toList()
}