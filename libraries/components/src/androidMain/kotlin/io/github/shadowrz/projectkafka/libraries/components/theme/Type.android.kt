package io.github.shadowrz.projectkafka.libraries.components.theme

import android.os.Build

internal actual fun useVariableFonts(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
