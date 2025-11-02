package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.imageCropper
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugins
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable

@AssistedInject
@ContributesComponent(AppScope::class)
class AddDetailsComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
    presenterFactory: AddDetailsPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
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
}
