package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.rtamayo.compose_material3_pickers.date.models.Month
import com.rtamayo.compose_material3_pickers.date.utils.DateFormatter.format
import com.rtamayo.compose_material3_pickers.date.utils.DateMapper
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarInput(
    date: LocalDate,
    monthList: List<Month>,
    onDateChanged: (LocalDate) -> Unit,
) {
    Column {
        var showYearSelector by remember { mutableStateOf(false) }
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        var currentPage by remember {
            mutableStateOf(0)
        }
        LaunchedEffect(key1 = true) {
            scope.launch {
                currentPage = DateMapper.getPage(monthList, date)
                pagerState.scrollToPage(currentPage)
            }
        }
        val currentMonth = monthList[pagerState.currentPage]
        CalendarTopBar(
            showYearSelector = showYearSelector,
            month = currentMonth,
            onYearClick = {
                showYearSelector = !showYearSelector
            },
            onPreviousMonth = {
                scope.launch {
                    if (currentPage > 0) currentPage -= 1
                    pagerState.scrollToPage(currentPage)
                }
            },
            onNextMonth = {
                scope.launch {
                    if(currentPage < monthList.size) currentPage += 1
                    pagerState.scrollToPage(currentPage)
                }
            }
        )
        if (showYearSelector) {
            YearGrid(
                month = monthList[pagerState.currentPage],
                monthList = monthList,
                // TODO: Make this jump user to same month but of selected year or in default the closest month
                onYearSelected = { month, year ->
                    showYearSelector = false
                    val currentYear = monthList[pagerState.currentPage].year
                    val selectedMonth = DateMapper.getMonth(monthList, currentYear, month, year)
                    currentPage = monthList.indexOf(selectedMonth)
                    scope.launch {
                        pagerState.scrollToPage(currentPage)
                    }
                }
            )
        } else {
            Calendar(
                date,
                monthList,
                pagerState,
                onDateChanged = onDateChanged
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CalendarTopBar(
    showYearSelector: Boolean,
    month: Month,
    onYearClick: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        AssistChip(
            onClick = onYearClick,
            label = {
                val date = LocalDate.of(month.year, month.monthName, 1)
                Text(text = format(date, "MMMM yyyy"))
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = ""
                )
            },
            shape = CircleShape,
            border = null
        )
        Spacer(modifier = Modifier.weight(1F))
        if (!showYearSelector) {
            IconButton(onClick = onPreviousMonth) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = ""
                )
            }
            IconButton(onClick = onNextMonth) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}