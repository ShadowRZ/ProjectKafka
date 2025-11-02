package io.github.shadowrz.projectkafka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUIFactories
import io.github.shadowrz.projectkafka.libraries.architecture.LocalComponentUIFactories

@Composable
@NonRestartableComposable
fun ProvideComponentUIFactories(
    uiFactories: ComponentUIFactories,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalComponentUIFactories provides uiFactories.uiFactories(),
    ) {
        content()
    }
}
