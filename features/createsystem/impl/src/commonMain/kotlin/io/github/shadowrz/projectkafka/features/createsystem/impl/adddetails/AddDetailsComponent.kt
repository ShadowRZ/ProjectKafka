package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.imageCropper
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.architecture.plugins
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable

@AssistedInject
class AddDetailsComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: AddDetailsPresenter.Factory,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onFinish(id: SystemID)
    }

    internal val callback = plugins<Callback>().first()

    internal val croppers =
        retainedInstance {
            Croppers()
        }

    @Serializable
    data class Params(
        val systemName: String,
    ) : Parameters

    private val paramters: Params = paramters()

    internal val presenter =
        presenterFactory.create(
            paramters.systemName,
            callback,
            croppers,
        )

    data class Croppers(
        val avatar: ImageCropper = imageCropper(),
        val cover: ImageCropper = imageCropper(),
    ) : InstanceKeeper.Instance

    @ContributesIntoMap(
        AppScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(AddDetailsComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<AddDetailsComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): AddDetailsComponent
    }
}
