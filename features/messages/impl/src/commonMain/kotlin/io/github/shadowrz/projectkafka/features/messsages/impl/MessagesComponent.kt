package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.runtime.Stable
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.features.messages.api.MessagesEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Stable
@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class MessagesComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: MessagesPresenter.Factory,
) :
    Component(
        context = context,
        plugins = plugins,
    ) {
    private val params = paramters<MessagesEntryPoint.Params>()
    // private val callback = plugin<MessagesEntryPoint.Callback>()

    internal val presenter = presenterFactory.create(params.chatID)
}
