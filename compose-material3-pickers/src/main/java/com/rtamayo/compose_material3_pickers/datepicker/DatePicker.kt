package com.rtamayo.compose_material3_pickers.datepicker

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.datepicker.DateMapper.getDateRange
import com.rtamayo.compose_material3_pickers.datepicker.components.CalendarInputSelector
import com.rtamayo.compose_material3_pickers.datepicker.components.Day
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
fun DatePicker(
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN,
    maxDate: LocalDate = LocalDate.MAX,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val currentDate by remember { mutableStateOf(startDate) }

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
            val calendarRange = getDateRange(minDate, maxDate)

            DatePickerContent()
        }
    )
}

@Composable
internal fun DatePickerContent() {

    var showCalendar by remember { mutableStateOf(true) }
    Column {
        CalendarInputSelector(
            LocalDate.now()
        ) { isShowingCalendar ->
            showCalendar = isShowingCalendar
        }
        if(showCalendar) {
            CalendarSelector(
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
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Column {
        var showYearSelector by remember { mutableStateOf(false) }
        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // TODO: Make custom button
            AssistChip(
                onClick = {
                    showYearSelector = !showYearSelector
                },
                label = {
                    Text(text = "November 2022")
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
                        pagerState.animateScrollToPage(5)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = ""
                    )
                }
                IconButton(onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
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
            count = 10,
            state = pagerState
        ) { page ->

            val currentMonth = List(date.lengthOfMonth()) { day ->  }

            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                columns = GridCells.Fixed(7)
            ) {
                items(daysInTheWeek) { day ->
                    Day(value = day, {})
                }
            }
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