package com.rtamayo.compose_material3_pickers.timepicker

import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.PickerDialog
import kotlin.math.roundToInt

@Composable
fun TimePicker() {
    PickerDialog(
        onDismissRequest = { /*TODO*/ },
        title = { /*TODO*/ },
        confirmButton = { /*TODO*/ },
        dismissButton = { /*TODO*/ },
        content = {

        }
    )
}

@Composable
fun TimePickerContent() {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .aspectRatio(1F)
            .fillMaxSize()
    ) {
        var offsetX by remember { mutableStateOf(0F) }
        var offsetY by remember { mutableStateOf(0F) }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}