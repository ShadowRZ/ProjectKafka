package io.github.shadowrz.projectkafka.libraries.localepicker

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.core.os.LocaleListCompat
import io.github.shadowrz.projectkafka.libraries.androidutils.LocaleConfigCompat

@Composable
actual fun rememberLocalePicker(): LocalePickerState {
    if (LocalInspectionMode.current) return LocalePickerState.EMPTY

    val context = LocalContext.current
    val activity = LocalActivity.current
    val localeListCompat = remember { LocaleConfigCompat(context) }
    var openDialog by remember { mutableStateOf(false) }

    return AndroidLocalePickerState(
        applicationLocale = AppCompatDelegate.getApplicationLocales(),
        supportedLocales =
            localeListCompat.supportedLocales ?: LocaleListCompat.getEmptyLocaleList(),
        openDialog = openDialog,
        openPlatformPicker = @RequiresApi(Build.VERSION_CODES.TIRAMISU) {
            activity?.startActivity(
                Intent(
                    Settings.ACTION_APP_LOCALE_SETTINGS,
                    Uri.fromParts("package", context.packageName, null),
                ),
            )
        },
        onOpenDialog = { openDialog = true },
        onPickLocale = {
            val localeList =
                if (it == null) {
                    LocaleListCompat.getEmptyLocaleList()
                } else {
                    LocaleListCompat.create(it)
                }
            openDialog = false
            AppCompatDelegate.setApplicationLocales(localeList)
        },
        onCloseDialog = { openDialog = false },
    )
}

@Composable
actual fun LocalePicker(
    state: LocalePickerState,
    modifier: Modifier,
) {
    if (LocalInspectionMode.current) return

    if ((state as AndroidLocalePickerState).openDialog) {
        LocaleDialog(
            applicationLocale = state.applicationLocale,
            supportedLocales = state.supportedLocales,
            modifier = modifier,
            onConfirm = { state.onPickLocale(it) },
            onDismissRequest = { state.onCloseDialog() },
        )
    }
}
