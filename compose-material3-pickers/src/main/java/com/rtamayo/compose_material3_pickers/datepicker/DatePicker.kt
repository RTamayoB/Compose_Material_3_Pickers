package com.rtamayo.compose_material3_pickers.datepicker

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getDateRange
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getMonthList
import com.rtamayo.compose_material3_pickers.datepicker.components.CalendarInputSelector
import com.rtamayo.compose_material3_pickers.datepicker.components.Day
import com.rtamayo.compose_material3_pickers.datepicker.models.Month
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@Composable
fun DatePicker(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN,
    maxDate: LocalDate = LocalDate.MAX,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val currentDate by remember { mutableStateOf(startDate) }
    val range = getDateRange(startDate, maxDate)

    val monthList = getMonthList(range)

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
                monthList = monthList
            )
        }
    )
}

@Composable
internal fun DatePickerContent(
    monthList: List<Month>
) {

    var showCalendar by remember { mutableStateOf(true) }
    Column {
        CalendarInputSelector(
            LocalDate.now()
        ) { isShowingCalendar ->
            showCalendar = isShowingCalendar
        }
        if(showCalendar) {
            CalendarSelector(
                monthList = monthList,
                onNextMonth = {},
                onPreviousMonth = {}
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
    monthList: List<Month>,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Column {
        var showYearSelector by remember { mutableStateOf(false) }
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        var currentPage by remember {
            mutableStateOf(0)
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // TODO: Make custom button
            AssistChip(
                onClick = {
                    showYearSelector = !showYearSelector
                },
                label = {
                    Text(text = monthList[pagerState.currentPage].monthName.name)
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
                        pagerState.animateScrollToPage(currentPage)
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
                        pagerState.animateScrollToPage(currentPage)
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
            YearGrid()
        } else {
            Calendar(
                LocalDate.now(),
                monthList,
                pagerState
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

val years = List(100) { i -> "${i+2022}" }

@Composable
fun YearGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(years) { year ->
            YearItem(year = year)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearItem(
    year: String,
) {
    TextButton(modifier = Modifier, onClick = {}) {
        Text(
            text = year,
            textAlign = TextAlign.Center
        )
    }

}

val weekLabels = listOf("D", "L", "M", "M", "J", "V", "S")
val daysInTheWeek = List(31) { i -> "${i+1}" }

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Calendar(
    date: LocalDate,
    monthList: List<Month>,
    pagerState: PagerState
) {
    Column {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(
                items = weekLabels
            ) {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        }
        HorizontalPager(
            count = monthList.size,
            state = pagerState
        ) { page ->
            val month = monthList[page]
            CalendarGrid(month)
        }
    }

}

@Composable
fun CalendarGrid(
    month: Month
) {
    val dayOfWeek = month.firstDayOfTheWeek.value
    Log.d("Saya", "${month.monthName.name} - $dayOfWeek")
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Fixed(7)
    ) {
        items(dayOfWeek) {
            Box(modifier = Modifier)
        }
        items(month.days!!) { day ->
            Day(value = day.toString(), {})
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