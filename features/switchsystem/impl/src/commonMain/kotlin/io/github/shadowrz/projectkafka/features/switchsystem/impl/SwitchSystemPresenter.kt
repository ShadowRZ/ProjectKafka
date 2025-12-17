package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemEntryPoint
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import kotlinx.coroutines.flow.map

@AssistedInject
class SwitchSystemPresenter(
    systemsStore: SystemsStore,
    @Assisted private val callback: SwitchSystemEntryPoint.Callback,
) : Presenter<SwitchSystemState> {
    private val systemsFlow = systemsStore.getSystems().map { Result.Success(it) }

    @Composable
    override fun present(): SwitchSystemState {
        val systems by systemsFlow.collectAsStateWithLifecycle(initialValue = Result.Loading)

        return SwitchSystemState(
            systems = systems,
        ) {
            when (it) {
                SwitchSystemEvents.CreateSystem -> callback.onCreateSystem()
                is SwitchSystemEvents.SwitchSystem -> callback.onSwitchSystem(it.id)
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(callback: SwitchSystemEntryPoint.Callback): SwitchSystemPresenter
    }
}
