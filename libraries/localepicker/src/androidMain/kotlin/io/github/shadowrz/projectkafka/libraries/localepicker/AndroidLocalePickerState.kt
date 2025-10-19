package io.github.shadowrz.projectkafka.libraries.localepicker

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import androidx.core.os.LocaleListCompat
import java.util.Locale

@Stable
data class AndroidLocalePickerState(
    val openDialog: Boolean,
    val applicationLocale: LocaleListCompat,
    val supportedLocales: LocaleListCompat,
    @RequiresApi(Build.VERSION_CODES.TIRAMISU) val openPlatformPicker: () -> Unit = { },
    val onPickLocale: (Locale?) -> Unit = { },
    val onOpenDialog: () -> Unit = { },
    val onCloseDialog: () -> Unit = { },
) : LocalePickerState {
    @ChecksSdkIntAtLeast(Build.VERSION_CODES.TIRAMISU)
    override fun openLocalePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            openPlatformPicker()
        } else {
            onOpenDialog()
        }
    }
}
