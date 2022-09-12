package com.rtamayo.compose_material3_pickers.datepicker.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Day(
    value: String,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    isCurrent: Boolean = false
) {
    IconButton(
        onClick = { /*TODO*/ },
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(36.dp)
                .aspectRatio(1F)
                .border(1.dp, Color.Blue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                textAlign = TextAlign.Center
            )
        }
    }
}