package io.github.shadowrz.projectkafka.features.licenses.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner

@AssistedInject
@ContributesComponent(AppScope::class)
class LicensesComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: LicensesPresenter,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ),
    OnBackCallbackOwner
