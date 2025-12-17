package io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.ImageCropper
import com.attafitamim.krop.core.crop.imageCropper
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.hanekokoro.framework.runtime.context.HanekokoroContext
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin.plugin
import io.github.shadowrz.projectkafka.libraries.architecture.Parameters
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import kotlinx.serialization.Serializable

@AssistedInject
@HanekokoroInject.ContributesComponent(AppScope::class)
class AddDetailsComponent(
    @Assisted context: HanekokoroContext,
    @Assisted plugins: List<Plugin>,
    presenterFactory: AddDetailsPresenter.Factory,
) : Component(
        context = context,
        plugins = plugins,
    ) {
    interface Callback : Plugin {
        fun onFinish(id: SystemID)
    }

    internal val callback = plugin<Callback>()

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
