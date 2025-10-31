package io.github.shadowrz.projectkafka.features.datamanage.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter

@AssistedInject
@ContributesComponent(AppScope::class)
class DataManageComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    internal val presenter: Presenter<DataManageState>,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    OnBackCallbackOwner
