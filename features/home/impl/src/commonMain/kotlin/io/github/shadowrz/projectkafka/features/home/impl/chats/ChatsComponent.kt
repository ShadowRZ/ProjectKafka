package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.instancekeeper.getOrCreate
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.RetainedCoroutineScope
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
class ChatsComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: ChatsPresenter.Factory,
    coroutineDispatchers: CoroutineDispatchers,
    private val chatsStore: ChatsStore,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    private val retainedCoroutineScope = instanceKeeper.getOrCreate { RetainedCoroutineScope(coroutineDispatchers.main + SupervisorJob()) }

    private val chats =
        Pager(
            PagingConfig(pageSize = PAGES),
        ) {
            chatsStore.getChats()
        }.flow.cachedIn(retainedCoroutineScope)

    internal val presenter = presenterFactory.create(chats)

    @ContributesIntoMap(
        SystemScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(ChatsComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<ChatsComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): ChatsComponent
    }
}
