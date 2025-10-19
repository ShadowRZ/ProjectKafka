package io.github.shadowrz.projectkafka.libraries.components

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.Instant
import java.time.ZoneOffset

@Composable
actual fun DatePickerField(
    value: LocalDate?,
    onValueChange: (LocalDate?) -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    readOnly: Boolean,
    textStyle: TextStyle,
    label: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)?,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    prefix: @Composable (() -> Unit)?,
    suffix: @Composable (() -> Unit)?,
    supportingText: @Composable (() -> Unit)?,
    isError: Boolean,
) {
    var showModal by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value?.toString() ?: "",
        onValueChange = {},
        enabled = enabled,
        readOnly = true,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        modifier =
            modifier.pointerInput(value) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null && enabled && !readOnly) {
                        showModal = true
                    }
                }
            },
    )

    if (showModal) {
        DatePickerModal(
            initialSelectedDate = value?.toJavaLocalDate(),
            onDateChange = {
                val date =
                    it?.let {
                        Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
                    }
                onValueChange(date?.toKotlinLocalDate())
            },
            onDismiss = { showModal = false },
        )
    }
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickerModal(
    initialSelectedDate: java.time.LocalDate? = null,
    onDateChange: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val datePickerState =
        rememberDatePickerState(
            initialSelectedDate = initialSelectedDate,
        )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateChange(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(
                    stringResource(CommonStrings.common_ok),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(CommonStrings.common_cancel),
                )
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}
