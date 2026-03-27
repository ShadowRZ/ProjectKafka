package io.github.shadowrz.projectkafka.designsystem.internal

import android.os.Build

internal actual fun useVariableFonts(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
