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
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@AssistedInject
@ContributesComponent(SystemScope::class)
class AddMemberComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: AddMemberPresenter.Factory,
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

    private val imageCropper =
        retainedInstance {
            InstanceKeeper.SimpleInstance(imageCropper())
        }

    internal val presenter =
        presenterFactory.create(
            callback = callback,
            imageCropper = imageCropper.instance,
        )
}
