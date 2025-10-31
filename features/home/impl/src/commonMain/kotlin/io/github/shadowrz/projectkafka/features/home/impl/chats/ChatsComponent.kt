package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.retainedCoroutineScope
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
@ContributesComponent(SystemScope::class)
class ChatsComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: ChatsPresenter.Factory,
    coroutineDispatchers: CoroutineDispatchers,
    private val chatsStore: ChatsStore,
) : Component(
        componentContext = componentContext,
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
