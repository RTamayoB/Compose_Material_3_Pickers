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
@OptIn(ExperimentalComposeUiApi::class)
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
    isFullScreen: Boolean = false,
    containerColor: Color = AlertDialogDefaults.containerColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    contentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = if (isFullScreen) DialogProperties(usePlatformDefaultWidth = false) else DialogProperties()
    ) {
        PickerDialogContent(
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(ButtonsMainAxisSpacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    neutralButton?.invoke()
                    Spacer(modifier = Modifier.weight(1F))
                    dismissButton()
                    confirmButton()
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
            isFullScreen = isFullScreen,
            containerColor = containerColor,
            tonalElevation = tonalElevation,
            titleContentColor = titleContentColor,
            contentColor = contentColor
        )
    }
}

@Composable
private fun PickerDialogContent(
    title: (@Composable () -> Unit),
    content: @Composable (() -> Unit),
    topButtons: @Composable () -> Unit,
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    tonalElevation: Dp,
    titleContentColor: Color,
    contentColor: Color,
    isFullScreen: Boolean
) {
    var surfaceModifier = modifier
    surfaceModifier = if (isFullScreen) {
        surfaceModifier.fillMaxSize()
    }
    else {
        surfaceModifier.wrapContentHeight()
    }
    Surface(
        modifier = surfaceModifier,
        shape = if (isFullScreen) RectangleShape else AlertDialogDefaults.shape,
        color = containerColor,
        tonalElevation = tonalElevation
    ) {
        Column(
            modifier = modifier
                .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth, maxHeight = MaxHeight)
                .padding(DialogPadding)
        ) {
            if (isFullScreen) {
                val textStyle = MaterialTheme.typography.labelLarge
                ProvideTextStyle(value = textStyle, content = topButtons)
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
            if(!isFullScreen) {
                val textStyle = MaterialTheme.typography.labelLarge
                ProvideTextStyle(value = textStyle, content = buttons)
            }
        }
    }
}

private val MinWidth = 280.dp
private val MaxWidth = 560.dp
private val MaxHeight = 512.dp
private val DialogPadding = PaddingValues(all = 12.dp)
private val TitlePadding = PaddingValues( start = 12.dp, bottom = 16.dp)
private val ButtonsMainAxisSpacing = 8.dp