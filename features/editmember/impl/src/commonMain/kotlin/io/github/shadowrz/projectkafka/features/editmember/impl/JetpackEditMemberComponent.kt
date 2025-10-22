package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.jetpackcomponentcontext.JetpackComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.retainedInstance
import com.attafitamim.krop.core.crop.imageCropper
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Plugin
import io.github.shadowrz.projectkafka.libraries.architecture.PluginsOwner
import io.github.shadowrz.projectkafka.libraries.architecture.paramters
import io.github.shadowrz.projectkafka.libraries.architecture.plugin

/**
 * Implmented to allow Jetpack Lifecycle to participate
 * (for [androidx.lifecycle.compose.collectAsStateWithLifecycle]).
 */
@OptIn(ExperimentalDecomposeApi::class)
class JetpackEditMemberComponent(
    componentContext: JetpackComponentContext,
    override val plugins: List<Plugin>,
    presenterFactory: EditMemberPresenter.Factory,
) : JetpackComponentContext by componentContext,
    PluginsOwner,
    OnBackCallbackOwner {
    private val params = paramters<EditMemberEntryPoint.Params>()

    private val callback = plugin<EditMemberComponent.Callback>()

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
}
