package com.rtamayo.compose_material3_pickers.date.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.models.Week
import com.rtamayo.compose_material3_pickers.date.range.DateRangePickerState
import com.rtamayo.compose_material3_pickers.date.DateRangePickerUiState
import java.time.LocalDate

@Composable
fun WeekSelected(
    week: Week,
    currentWeekStart: LocalDate,
    state: DateRangePickerUiState,
    selectedPercentageTotalProvider: () -> Float,
    modifier: Modifier = Modifier,
    widthPerDay: Dp = 40.dp,
    pillColor: Color = androidx.compose.material.MaterialTheme.colors.secondary
) {
    val widthPerDayPx = with(LocalDensity.current) { widthPerDay.toPx() }
    val cornerRadiusPx = with(LocalDensity.current) { 50.dp.toPx() }

    Canvas(
        modifier = modifier.fillMaxWidth(),
        onDraw = {
            val (offset, size) = getOffsetAndSize(
                this.size.width,
                state,
                currentWeekStart,
                week,
                widthPerDayPx,
                cornerRadiusPx,
                selectedPercentageTotalProvider()
            )

            val translationX = if (state.animateDirection?.isBackwards() == true) -size else 0f

            translate(translationX) {
                drawRoundRect(
                    color = pillColor,
                    topLeft = offset,
                    size = Size(size, 35.dp.toPx()),
                    cornerRadius = CornerRadius(cornerRadiusPx)
                )
            }
        }
    )
}

/**
 * Calculates the animated Offset and Size of the red selection pill based on the CalendarState and
 * the Week SelectionState, based on the overall selectedPercentage.
 */
private fun getOffsetAndSize(
    width: Float,
    state: DateRangePickerUiState,
    currentWeekStart: LocalDate,
    week: Week,
    widthPerDayPx: Float,
    cornerRadiusPx: Float,
    selectedPercentage: Float
): Pair<Offset, Float> {
    val numberDaysSelected = state.getNumberSelectedDaysInWeek(currentWeekStart, week.yearMonth)
    val monthOverlapDelay = state.monthOverlapSelectionDelay(currentWeekStart, week)
    val dayDelay = state.dayDelay(currentWeekStart)
    val edgePadding = (width - widthPerDayPx * DateRangePickerState.DAYS_IN_WEEK) / 2

    val percentagePerDay = 1f / state.numberSelectedDays
    val startPercentage = (dayDelay + monthOverlapDelay) * percentagePerDay
    val endPercentage = startPercentage + numberDaysSelected * percentagePerDay

    val scaledPercentage = if (selectedPercentage >= endPercentage) {
        1f
    } else if (selectedPercentage < startPercentage) {
        0f
    } else {
        // Scale the overall percentage between the start selection of days to the end selection for
        // the current week. eg: if this week has 3 days before it selected, we only want to
        // start this animation after 3 * percentagePerDay and end it at the number of selected days
        // in the week - so we normalize the percentage between the startPercentage + endPercentage
        // to a range between at min 0f and 1f.
        normalize(
            selectedPercentage,
            startPercentage, endPercentage
        )
    }

    val scaledSelectedNumberDays = scaledPercentage * numberDaysSelected

    val sideSize = edgePadding + cornerRadiusPx

    val leftSize =
        if (state.isLeftHighlighted(currentWeekStart, week.yearMonth)) sideSize else 0f
    val rightSize =
        if (state.isRightHighlighted(currentWeekStart, week.yearMonth)) sideSize else 0f

    var totalSize = (scaledSelectedNumberDays * widthPerDayPx) +
            (leftSize + rightSize) * scaledPercentage
    if (dayDelay + monthOverlapDelay == 0 && numberDaysSelected >= 1) {
        totalSize = totalSize.coerceAtLeast(widthPerDayPx)
    }

    totalSize -= 50F

    val startOffset =
        state.selectedStartOffset(currentWeekStart, week.yearMonth) * widthPerDayPx

    val offset =
        if (state.animateDirection?.isBackwards() == true) {
            Offset((startOffset + edgePadding + rightSize), 18f)
        } else {
            Offset((startOffset + edgePadding - leftSize), 18f)
        }

    return offset to totalSize
}

internal const val DURATION_MILLIS_PER_DAY = 150

private fun normalize(x: Float, inMin: Float, inMax: Float, outMin: Float = 0f, outMax: Float = 1f): Float {
    val outRange = outMax - outMin
    val inRange = inMax - inMin
    return (x - inMin) * outRange / inRange + outMin
}