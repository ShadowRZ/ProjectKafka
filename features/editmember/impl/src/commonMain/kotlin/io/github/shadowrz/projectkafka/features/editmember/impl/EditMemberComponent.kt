package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.imageCropper
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.lifecycleOwner
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class EditMemberComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: EditMemberPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
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

    private val params = paramters<EditMemberEntryPoint.Params>()

    private val imageCropper =
        retainedInstance {
            InstanceKeeper.SimpleInstance(imageCropper())
        }

    internal val presenter =
        presenterFactory.create(
            memberID = params.memberID,
            callback = callback,
            imageCropper = imageCropper.instance,
        )

    internal val lifecycleOwner = lifecycleOwner()

    private val entryPointCallback = plugin<EditMemberEntryPoint.Callback>()

    internal fun onDeleteMember() {
        onBack()
        entryPointCallback.onDeleteMember()
    }
}
