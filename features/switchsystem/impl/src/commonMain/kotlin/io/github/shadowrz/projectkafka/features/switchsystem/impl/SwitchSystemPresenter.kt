package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import kotlinx.coroutines.flow.map

@Inject
class SwitchSystemPresenter(systemsStore: SystemsStore) : Presenter<SwitchSystemState> {
    private val systemsFlow = systemsStore.getSystems().map { AsyncOutcome.Success(it) }

    @Composable
    override fun present(): SwitchSystemState {
        val systems by systemsFlow.collectAsStateWithLifecycle(initialValue = AsyncOutcome.Loading)

        return SwitchSystemState(systems = systems)
    }
}
