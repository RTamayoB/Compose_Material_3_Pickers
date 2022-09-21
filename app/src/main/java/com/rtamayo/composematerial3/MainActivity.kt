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
                var endD by remember {
                    mutableStateOf(LocalDate.now().plusDays(5))
                }
                var showDialog by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = date.toString())
                        Button(onClick = {
                            showDialog = true
                        }) {
                            Text(text = "Get Date")
                        }
                    }
                }
                if (showDialog) {
                    /*
                    DatePicker(
                        startDate = date,
                        minDate = LocalDate.now(),
                        maxDate = LocalDate.now().plusYears(50),
                        onDateSelected = {
                            showDialog = false
                            date = it
                        },
                        onDismissRequest = {
                            showDialog = false
                        }
                    )
                    */
                    DateRangePicker(
                        startDate = date,
                        endDate = endD,
                        onDateSelected = { startDate, endDate ->
                            date = startDate
                            endD = endDate
                        },
                        onDismissRequest = {
                            showDialog = false
                        }
                    )
                }

            }
        }
    }
}