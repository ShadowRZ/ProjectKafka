package io.github.shadowrz.projectkafka.designsystem.internal

import android.os.Build

internal actual val PLATFORM_SUPPORTS_PREDICTIVE_BACK: Boolean =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM
