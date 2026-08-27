package io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLog
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLogStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(SystemScope::class)
class FrontLogsPresenter(private val frontLogStore: FrontLogStore) : Presenter<FrontLogsState> {
    @Composable
    override fun present(): FrontLogsState {
        val lifecycleOwner = LocalLifecycleOwner.current
        val frontLogs by
            produceState<AsyncOutcome<List<FrontLog>>>(initialValue = AsyncOutcome.Loading) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    frontLogStore.getFrontLogs().map { AsyncOutcome.Success(it) }.collect { this@produceState.value = it }
                }
            }

        return FrontLogsState(frontLogs = frontLogs)
    }
}
