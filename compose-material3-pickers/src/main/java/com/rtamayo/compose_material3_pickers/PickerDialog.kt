package com.rtamayo.compose_material3_pickers

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

//TODO Change buttons to: confirmButton, dismissButton,
// neutralButton (opt), topConfirmButton (opt) and topDismissButton (opt)
// then, just show the top bar if any top button is shown and shape is Rectangle Shape
// Note: Already implemented, improve this
@Composable
internal fun PickerDialog(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit),
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    neutralButton: @Composable (() -> Unit)? = null,
    topConfirmButton: @Composable (() -> Unit)? = null,
    topDismissButton: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    showCalendar: Boolean = true,
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
                if(showCalendar) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonsMainAxisSpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        neutralButton?.invoke()
                        Spacer(modifier = Modifier.weight(1F))
                        dismissButton()
                        confirmButton()
                    }
                }
            },
            modifier = modifier,
            topButtons = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    topDismissButton?.invoke()
                    Spacer(modifier = Modifier.weight(1F))
                    topConfirmButton?.invoke()
                }
            },
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun FullScreenPickerDialog(
    onDismissRequest: () -> Unit,
    title: @Composable (() -> Unit),
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = AlertDialogDefaults.containerColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    contentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        FullScreenPickerDialogContent(
            buttons = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    dismissButton()
                    Spacer(modifier = Modifier.weight(1F))
                    confirmButton()
                }
            },
            modifier = modifier,
            title = title,
            content = content,
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
    topButtons: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit),
    content: @Composable (() -> Unit),
    shape: Shape,
    containerColor: Color,
    tonalElevation: Dp,
    titleContentColor: Color,
    contentColor: Color,
) {
    Surface(
        modifier = modifier
            .wrapContentHeight(),
        shape = shape,
        color = containerColor,
        tonalElevation = tonalElevation
    ) {
        Column(
            modifier = modifier
                .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth, maxHeight = MaxHeight)
                .padding(DialogPadding)
        ) {
            if (shape == RectangleShape) {
               topButtons?.invoke()
            }
            CompositionLocalProvider(LocalContentColor provides titleContentColor ) {
                val textStyle = MaterialTheme.typography.labelSmall
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
                            .align(Alignment.CenterHorizontally)
                    ) {
                        content()
                    }
                }
            }
            if(shape == AlertDialogDefaults.shape) {
                val textStyle = MaterialTheme.typography.labelLarge
                ProvideTextStyle(value = textStyle, content = buttons)
            }
        }
    }
}

@Composable
private fun FullScreenPickerDialogContent(
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    containerColor: Color,
    tonalElevation: Dp,
    titleContentColor: Color,
    contentColor: Color,
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        shape = RectangleShape,
        color = containerColor,
        tonalElevation = tonalElevation
    ) {
        Column(
            modifier = modifier.padding(DialogPadding)
        ) {
            val buttonsTextStyle = MaterialTheme.typography.labelLarge
            ProvideTextStyle(value = buttonsTextStyle, content = buttons)
            CompositionLocalProvider(LocalContentColor provides titleContentColor ) {
                val textStyle = MaterialTheme.typography.labelSmall
                ProvideTextStyle(textStyle) {
                    Box(
                        Modifier
                            .padding(FullScreenTitlePadding)
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
                            .align(Alignment.CenterHorizontally)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

private val MinWidth = 280.dp
private val MaxWidth = 560.dp
private val MaxHeight = 512.dp
private val DialogPadding = PaddingValues(all = 12.dp)
private val TitlePadding = PaddingValues( start = 12.dp, bottom = 16.dp)
private val FullScreenTitlePadding = PaddingValues(start = 60.dp, end = 4.dp)
private val ButtonsMainAxisSpacing = 8.dp