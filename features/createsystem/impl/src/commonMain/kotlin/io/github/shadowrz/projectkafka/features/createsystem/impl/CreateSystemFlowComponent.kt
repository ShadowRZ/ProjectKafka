package io.github.shadowrz.projectkafka.features.createsystem.impl

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemEntryPoint
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsPresenter
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemPresenter
import io.github.shadowrz.projectkafka.libraries.core.log.logger.LoggerTag
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Stable
@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class CreateSystemFlowComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    internal val createSystemPresenter: CreateSystemPresenter,
    internal val addDetailsPresenterFactory: AddDetailsPresenter.Factory,
) :
    Component(
        context = context,
        plugins = plugins,
    ) {
    private val logger = LoggerTag.Root

    internal val callback = plugin<CreateSystemEntryPoint.Callback>()

    internal fun onBack() {
        onNavigateUp {}
    }

    internal fun onFinish(id: SystemID) {
        Logger.withTag(logger.value).d { "Created system with ID $id" }
        callback.onFinished(id)
    }

    @Serializable
    sealed interface NavTarget : NavKey {
        @Serializable data object CreateSystem : NavTarget

        @Serializable data class AddDetails(val systemName: String) : NavTarget

        companion object {
            internal val CONFIG = SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(NavKey::class) {
                        subclass(CreateSystem::class, CreateSystem.serializer())
                        subclass(AddDetails::class, AddDetails.serializer())
                    }
                }
            }
        }
    }
}
