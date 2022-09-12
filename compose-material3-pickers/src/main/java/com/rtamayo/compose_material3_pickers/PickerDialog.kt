package com.rtamayo.compose_material3_pickers

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
internal fun PickerDialog(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit),
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    neutralButton: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    contentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties()
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        PickerDialogContent(
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(ButtonsMainAxisSpacing),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    neutralButton?.invoke()
                    Spacer(modifier = Modifier.weight(1F))
                    dismissButton()
                    confirmButton()
                }
            },
            modifier = modifier,
            title = title,
            content = content,
            shape = shape,
            containerColor = containerColor,
            tonalElevation = tonalElevation,
            titleContentColor = titleContentColor,
            contentColor = contentColor
        )
    }
}

@Composable
private fun PickerDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit),
    content: @Composable (() -> Unit),
    shape: Shape,
    containerColor: Color,
    tonalElevation: Dp,
    titleContentColor: Color,
    contentColor: Color,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        tonalElevation = tonalElevation
    ) {
        Column(
            modifier = modifier
                .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth)
                .padding(DialogPadding)
        ) {
            CompositionLocalProvider(LocalContentColor provides titleContentColor ) {
                val textStyle = MaterialTheme.typography.headlineSmall
                ProvideTextStyle(textStyle) {
                    Box(
                        Modifier
                            .padding(TitlePadding)
                            .align(Alignment.Start)
                    ) {
                        title()
                    }
                }
            }
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                val textStyle =
                    MaterialTheme.typography.bodyMedium
                ProvideTextStyle(textStyle) {
                    Box(
                        Modifier
                            .weight(weight = 1f, fill = false)
                            .padding(ContentPadding)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        content()
                    }
                }
            }
            val textStyle = MaterialTheme.typography.labelLarge
            ProvideTextStyle(value = textStyle, content = buttons)
        }
    }
}

private val MinWidth = 280.dp
private val MaxWidth = 560.dp
private val DialogPadding = PaddingValues(all = 24.dp)
private val TitlePadding = PaddingValues(bottom = 16.dp)
private val ContentPadding = PaddingValues(bottom = 24.dp)
private val ButtonsMainAxisSpacing = 8.dp