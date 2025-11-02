package io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter

@Inject
@ContributesBinding(AppScope::class)
class CreateSystemPresenter : Presenter<CreateSystemState> {
    @Composable
    override fun present(): CreateSystemState {
        val textFieldState = rememberTextFieldState()
        val valid by remember {
            derivedStateOf {
                !textFieldState.text.isEmpty()
            }
        }

        return CreateSystemState(
            textFieldState = textFieldState,
            valid = valid,
        )
    }
}
