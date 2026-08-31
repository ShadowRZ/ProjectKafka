package io.github.shadowrz.projectkafka.libraries.kafkastate.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.SystemsPresenter
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.SystemsState
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(SystemScope::class)
class DefaultSystemsPresenter(private val systemsStore: SystemsStore) : SystemsPresenter {
    @Composable
    override fun present(): SystemsState {
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

        return SystemsState(systems = systems)
    }
}
