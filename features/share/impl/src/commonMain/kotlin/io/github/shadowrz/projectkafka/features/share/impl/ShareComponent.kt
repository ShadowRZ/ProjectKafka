package io.github.shadowrz.projectkafka.features.share.impl

import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.projectkafka.features.share.api.ShareEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class ShareComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    internal val params = paramters<ShareEntryPoint.Params>()
}
