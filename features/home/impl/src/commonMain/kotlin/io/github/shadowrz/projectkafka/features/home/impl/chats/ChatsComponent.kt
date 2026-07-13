package io.github.shadowrz.projectkafka.features.home.impl.chats

import com.arkivanov.decompose.ExperimentalDecomposeApi
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(
    ExperimentalDecomposeApi::class,
    ExperimentalSerializationApi::class,
)
@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class ChatsComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val presenter: ChatsPresenter,
) :
    Component(
        context = context,
        plugins = plugins,
    )
