package io.github.shadowrz.projectkafka.features.datamanage.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageEntryPoint

@Inject
@ContributesBinding(AppScope::class)
class DefaultDataManageEntryPoint : DataManageEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
    ): Component =
        parent.childComponent<DataManageComponent>(
            context = context,
        )
}
