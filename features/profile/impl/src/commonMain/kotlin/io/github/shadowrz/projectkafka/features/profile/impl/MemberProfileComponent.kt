package io.github.shadowrz.projectkafka.features.profile.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.retainedSimpleInstance
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class MemberProfileComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: MemberProfilePresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ),
    OnBackCallbackOwner {
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
