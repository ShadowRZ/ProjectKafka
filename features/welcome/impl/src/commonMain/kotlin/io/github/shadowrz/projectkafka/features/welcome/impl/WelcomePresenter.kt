package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter
import io.github.shadowrz.projectkafka.libraries.localepicker.rememberLocalePicker

@Inject
@ContributesBinding(AppScope::class)
class WelcomePresenter : Presenter<WelcomeState> {
    @Composable
    override fun present(): WelcomeState {
        var showHelp by remember { mutableStateOf(false) }

        val localePickerState = rememberLocalePicker()

        return WelcomeState(
            localePickerState = localePickerState,
            showHelp = showHelp,
        ) {
            when (it) {
                is WelcomeEvents.ShowHelp -> showHelp = it.visible
            }
        }
    }
}
