package io.github.shadowrz.projectkafka.features.share.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.share.api.ShareData
import io.github.shadowrz.projectkafka.features.share.api.ShareEntryPoint
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
class DefaultShareEntryPoint : ShareEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        shareData: ShareData,
    ): Component =
        parent.childComponent<ShareComponent>(
            context = context,
            plugins = listOf(ShareEntryPoint.Params(shareData)),
        )
}
