package com.rtamayo.compose_material3_pickers.time

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.rtamayo.compose_material3_pickers.PickerDialog
import com.rtamayo.compose_material3_pickers.R
import java.time.LocalTime
import kotlin.math.*

@Composable
fun TimePicker(
    time: LocalTime = LocalTime.now(),
    onTimeSelected: (time: LocalTime) -> Unit,
    onDismissRequest: () -> Unit,
) {
    PickerDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Select Time")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(time)
                }
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        content = {
            TimePickerContent()
        }
    )
}

@Composable
fun TimePickerContent() {

    Box(modifier = Modifier.size(256.dp).background(Color.Gray, shape = CircleShape)) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue, shape = CircleShape)
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

private fun Float.pxToDp(context: Context): Dp = (this / context.resources.displayMetrics.density).dp