package com.rtamayo.composematerial3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.date.DatePicker
import com.rtamayo.compose_material3_pickers.date.DateRangePicker
import com.rtamayo.compose_material3_pickers.time.TimePicker
import com.rtamayo.composematerial3.ui.theme.ComposeMaterial3Theme
import java.time.LocalDate
import kotlin.math.max
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMaterial3Theme {
                var date by remember { mutableStateOf(LocalDate.now()) }
                var startDate by remember { mutableStateOf(LocalDate.now()) }
                var endDate by remember { mutableStateOf(LocalDate.now().plusDays(5)) }
                var showDatePicker by remember { mutableStateOf(false) }
                var showDateRangePicker by remember { mutableStateOf(false) }
                var showTimePicker by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = date.toString())
                            Button(onClick = {
                                showDatePicker = true
                            }) {
                                Text(text = "Get Date")
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "$startDate - $endDate")
                            Button(onClick = {
                                showDateRangePicker = true
                            }) {
                                Text(text = "Get Date Range")
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                showTimePicker = true
                            }) {
                                Text(text = "Get Time")
                            }
                        }
                    }
                }
                if (showDatePicker) {
                    DatePicker(
                        startDate = date,
                        onDateSelected = {
                            showDatePicker = false
                            date = it
                        },
                        onDismissRequest = {
                            showDatePicker = false
                        }
                    )
                }
                if(showDateRangePicker) {
                    DateRangePicker(
                        startDate = startDate,
                        endDate = endDate,
                        onDateSelected = { start, end ->
                            startDate = start
                            endDate = end
                            showDateRangePicker = false
                        },
                        onDismissRequest = {
                            showDateRangePicker = false
                        }
                    )
                }
                if (showTimePicker) {
                    TimePicker(
                        onTimeSelected = {
                            showTimePicker = false
                        },
                        onDismissRequest = {
                            showTimePicker = false
                        }
                    )
                }
            }
        }
    }
}