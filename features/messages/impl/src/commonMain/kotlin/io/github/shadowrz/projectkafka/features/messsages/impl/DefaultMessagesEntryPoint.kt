package io.github.shadowrz.projectkafka.features.messsages.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.messages.api.MessagesEntryPoint
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
class DefaultMessagesEntryPoint : MessagesEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        chatID: ChatID,
        callback: MessagesEntryPoint.Callback,
    ): Component =
        parent.childComponent<MessagesComponent>(
            context = context,
            plugins =
                listOf(
                    MessagesEntryPoint.Params(chatID),
                    callback,
                ),
        )
}
