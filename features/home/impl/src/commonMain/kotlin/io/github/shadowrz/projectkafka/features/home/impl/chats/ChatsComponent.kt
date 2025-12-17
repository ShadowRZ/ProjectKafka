package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.arkivanov.decompose.ExperimentalDecomposeApi
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.coroutines.retainedCoroutineScope
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi

const val PAGES = 20

@OptIn(
    ExperimentalDecomposeApi::class,
    ExperimentalSerializationApi::class,
)
@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class ChatsComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: ChatsPresenter.Factory,
    coroutineDispatchers: CoroutineDispatchers,
    private val chatsStore: ChatsStore,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    private val retainedCoroutineScope = retainedCoroutineScope(coroutineDispatchers.main + SupervisorJob())

    private val chats =
        Pager(
            PagingConfig(pageSize = PAGES),
        ) {
            chatsStore.getChats()
        }.flow.cachedIn(retainedCoroutineScope)

    internal val presenter = presenterFactory.create(chats)
}
