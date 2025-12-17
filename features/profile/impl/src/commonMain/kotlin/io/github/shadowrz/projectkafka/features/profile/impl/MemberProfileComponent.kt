package io.github.shadowrz.projectkafka.features.profile.impl

import com.arkivanov.essenty.instancekeeper.retainedSimpleInstance
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class MemberProfileComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: MemberProfilePresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    private val params = paramters<MemberProfileEntryPoint.Params>()
    private val callback = plugin<MemberProfileEntryPoint.Callback>()

    internal val presenter =
        retainedSimpleInstance {
            presenterFactory.create(params.memberID)
        }

    internal fun onEdit() {
        callback.onEditMember()
    }
}
