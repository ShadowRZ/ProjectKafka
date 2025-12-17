package io.github.shadowrz.projectkafka.features.preferences.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesEntryPoint

@Inject
@ContributesBinding(AppScope::class)
class DefaultPreferencesEntryPoint : PreferencesEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        callback: PreferencesEntryPoint.Callback,
    ): Component =
        parent.childComponent<PreferencesComponent>(
            context = context,
            plugins = listOf(callback),
        )
}
