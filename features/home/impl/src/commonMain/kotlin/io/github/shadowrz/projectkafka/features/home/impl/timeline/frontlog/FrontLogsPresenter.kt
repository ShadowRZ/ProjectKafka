package io.github.shadowrz.projectkafka.features.home.impl.timeline.frontlog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.FrontLogStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(SystemScope::class)
class FrontLogsPresenter(
    frontLogStore: FrontLogStore,
    coroutineDispatchers: CoroutineDispatchers,
) : Presenter<FrontLogsState> {
    @Composable
    override fun present(): FrontLogsState {
        val frontLogs by frontLogsFlow.collectAsState(initial = Result.Loading)

        return FrontLogsState(
            frontLogs = frontLogs,
        )
    }

    private val frontLogsFlow =
        frontLogStore
            .getFrontLogs()
            .map { Result.Success(it) }
            .flowOn(coroutineDispatchers.computation)
}
