package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalNavigator: ProvidableCompositionLocal<Navigator> = compositionLocalOf {
    error("No LocalNavigator provided!")
}
