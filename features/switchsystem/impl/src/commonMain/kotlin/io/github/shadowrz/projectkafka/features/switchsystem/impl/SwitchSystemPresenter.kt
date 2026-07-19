package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import kotlinx.coroutines.flow.map

@Inject
class SwitchSystemPresenter(private val systemsStore: SystemsStore) : Presenter<SwitchSystemState> {

    @Composable
    override fun present(): SwitchSystemState {
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycle = lifecycleOwner.lifecycle
        val systems by
            produceState<AsyncOutcome<List<System>>>(AsyncOutcome.Loading, lifecycle) {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    systemsStore
                        .getSystems()
                        .map { AsyncOutcome.Success(it) }
                        .collect {
                            this@produceState.value = it
                        }
                }
            }

        return SwitchSystemState(systems = systems)
    }
}
