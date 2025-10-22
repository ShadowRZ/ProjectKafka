package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.imageCropper
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.HasBackHandler
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
class AddMemberComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: AddMemberPresenter.Factory,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    HasBackHandler {
    interface Callback : Plugin {
        fun onFinish()
    }

    private val callback = object : Callback {
        override fun onFinish() {
            onBack()
        }
    }

    private val imageCropper =
        retainedInstance {
            InstanceKeeper.SimpleInstance(imageCropper())
        }

    internal val presenter =
        presenterFactory.create(
            callback = callback,
            imageCropper = imageCropper.instance,
        )

    @ContributesIntoMap(
        SystemScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(AddMemberComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<AddMemberComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): AddMemberComponent
    }
}
