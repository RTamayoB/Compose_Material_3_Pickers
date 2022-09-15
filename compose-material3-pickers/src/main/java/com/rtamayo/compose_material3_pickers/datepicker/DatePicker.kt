package com.rtamayo.compose_material3_pickers.datepicker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getMonth
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getMonthList
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getPage
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getYearList
import com.rtamayo.compose_material3_pickers.datepicker.components.Calendar
import com.rtamayo.compose_material3_pickers.datepicker.components.CalendarInputSelector
import com.rtamayo.compose_material3_pickers.datepicker.components.Year
import com.rtamayo.compose_material3_pickers.datepicker.models.Month
import com.rtamayo.compose_material3_pickers.datepicker.utils.DateFormatter.formatMonth
import kotlinx.coroutines.launch
import java.time.LocalDate

//Use Ceil/Floor functions to return minus 100 years and plus 100 years
val MIN_DATE = LocalDate.now().minusYears(50)
val MAX_DATE = LocalDate.now().plusYears(50)

@Composable
fun DatePicker(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = MIN_DATE,
    maxDate: LocalDate = MAX_DATE,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {
    var currentDate by remember { mutableStateOf(startDate) }

    val monthList = getMonthList(minDate, maxDate)

    PickerDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = "Select Date",
                style = Typography().labelSmall
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(currentDate)
                }
            ) {
                Text(text = "Ok")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = "Cancel")
            }
        },
        content = {
            DatePickerContent(
                date = currentDate,
                monthList = monthList,
                onDateChanged = {
                    currentDate = it
                }
            )
        }
    )
}

@Composable
internal fun DatePickerContent(
    date: LocalDate,
    monthList: List<Month>,
    onDateChanged: (LocalDate) -> Unit,
) {

    var showCalendar by remember { mutableStateOf(true) }
    Column {
        CalendarInputSelector(
            localDate = date
        ) { isShowingCalendar ->
            showCalendar = isShowingCalendar
        }
        if(showCalendar) {
            CalendarSelector(
                date = date,
                monthList = monthList,
                onDateChanged = onDateChanged
            )
        }
        else {
            TextFieldSelector(
                value = "",
                onValueChange = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun CalendarSelector(
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
                currentPage = getPage(monthList, date)
                pagerState.scrollToPage(currentPage)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AssistChip(
                onClick = {
                    showYearSelector = !showYearSelector
                },
                label = {
                    val month = monthList[pagerState.currentPage]
                    Text(text = formatMonth(month.monthName.name, month.year))
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
                IconButton(onClick = {
                    scope.launch {
                        if (currentPage > 0) currentPage -= 1
                        pagerState.scrollToPage(currentPage)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {
                    scope.launch {
                        if(currentPage < monthList.size) currentPage += 1
                        pagerState.scrollToPage(currentPage)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = ""
                    )
                }
            }
        }
        if (showYearSelector) {
            YearGrid(
                month = monthList[pagerState.currentPage],
                monthList = monthList,
                // TODO: Make this jump user to same month but of selected year or in default the closest month
                onYearSelected = { month, year ->
                    showYearSelector = false
                    val currentYear = monthList[pagerState.currentPage].year
                    val selectedMonth = getMonth(monthList, currentYear, month, year)
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
fun TextFieldSelector(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(top = 16.dp),
        label = {
            Text(
                text = "Date",
                textAlign = TextAlign.Center
            )
        },
        placeholder = {
            Text(
                text = "dd/mm/yy"
            )
        }
    )
}

@Composable
fun YearGrid(
    month: Month,
    monthList: List<Month>,
    onYearSelected: (month: Month, year: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        //TODO: Get list of Years
        val yearList = getYearList(monthList)
        items(yearList) { year ->
            Year(
                month,
                year = year,
                onYearSelected = onYearSelected
            )
        }
    }
}

@Preview
@Composable
private fun DatePickerPreview() {
    DatePicker(
        onDateSelected = {

        },
        onDismissRequest = {

        }
    )
}