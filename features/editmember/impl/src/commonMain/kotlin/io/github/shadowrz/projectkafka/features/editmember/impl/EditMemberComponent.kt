package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.jetpackcomponentcontext.asJetpackComponentContext
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.projectkafka.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.Component
import io.github.shadowrz.projectkafka.libraries.architecture.GenericComponent
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.plugin
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class EditMemberComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted override val parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: EditMemberPresenter.Factory,
) : Component(
        componentContext = componentContext,
        plugins = plugins,
    ),
    OnBackCallbackOwner {
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
}
