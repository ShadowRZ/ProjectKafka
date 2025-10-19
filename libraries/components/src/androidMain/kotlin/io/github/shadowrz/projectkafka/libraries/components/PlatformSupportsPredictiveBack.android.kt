package io.github.shadowrz.projectkafka.libraries.components

import android.os.Build

actual val PLATFORM_SUPPORTS_PREDICTIVE_BACK =
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM
