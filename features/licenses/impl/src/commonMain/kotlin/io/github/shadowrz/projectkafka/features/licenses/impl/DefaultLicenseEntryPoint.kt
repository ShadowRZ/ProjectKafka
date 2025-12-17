package io.github.shadowrz.projectkafka.features.licenses.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.licenses.api.LicenseEntryPoint

@Inject
@ContributesBinding(AppScope::class)
class DefaultLicenseEntryPoint : LicenseEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
    ): Component =
        parent.childComponent<LicensesComponent>(
            context = context,
            plugins = emptyList(),
        )
}
