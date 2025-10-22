package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.jetpackcomponentcontext.asJetpackComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.stop
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.HasBackHandler
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
class EditMemberComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: Component?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: EditMemberPresenter.Factory,
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

    @OptIn(ExperimentalDecomposeApi::class)
    internal val jetpackComponent = JetpackEditMemberComponent(
        componentContext = childContext(
            key = "JetpackEditMemberComponent",
        ).asJetpackComponentContext(),
        plugins = plugins + listOf(callback),
        presenterFactory = presenterFactory,
    )

    private val entryPointCallback = plugin<EditMemberEntryPoint.Callback>()

    internal fun onDeleteMember() {
        onBack()
        entryPointCallback.onDeleteMember()
    }

    @ContributesIntoMap(
        SystemScope::class,
        binding = binding<Component.Factory<*>>(),
    )
    @ComponentKey(EditMemberComponent::class)
    @AssistedFactory
    interface Factory : Component.Factory<EditMemberComponent> {
        override fun create(
            context: ComponentContext,
            parent: Component?,
            plugins: List<Plugin>,
        ): EditMemberComponent
    }
}
