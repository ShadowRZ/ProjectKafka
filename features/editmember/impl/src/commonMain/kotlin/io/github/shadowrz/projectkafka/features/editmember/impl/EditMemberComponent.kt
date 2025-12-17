package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.imageCropper
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.lifecycle.lifecycleOwner
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@HanekokoroInject.ContributesComponent(SystemScope::class)
class EditMemberComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: EditMemberPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onFinish()
    }

    private val callback = object : Callback {
        override fun onFinish() {
            navigateUp()
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
        navigateUp()
        entryPointCallback.onDeleteMember()
    }
}
