package io.github.shadowrz.projectkafka.features.preferences.impl

import androidx.compose.runtime.Immutable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Component
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import io.github.shadowrz.hanekokoro.framework.runtime.Plugin
import io.github.shadowrz.hanekokoro.framework.runtime.plugin
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesEntryPoint
import io.github.shadowrz.projectkafka.libraries.architecture.OnBackCallbackOwner
import io.github.shadowrz.projectkafka.libraries.architecture.Resolver
import kotlinx.serialization.Serializable

@AssistedInject
@ContributesComponent(AppScope::class)
class PreferencesComponent(
    @Assisted context: ComponentContext,
    @Assisted parent: GenericComponent<*>?,
    @Assisted plugins: List<Plugin>,
) : Component(
        context = context,
        plugins = plugins,
        parent = parent,
    ),
    OnBackCallbackOwner,
    Resolver<PreferencesComponent.NavTarget, PreferencesComponent.Resolved> {
    private val callback = plugin<PreferencesEntryPoint.Callback>()

    internal fun onDataManage() {
        callback.onDataManage()
    }

    private val navigation = StackNavigation<NavTarget>()

    val childStack: Value<ChildStack<*, Resolved>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.Root,
            handleBackButton = true,
            childFactory = ::resolve,
        )

    override fun onBack() {
        navigation.pop {
            if (!it) super.onBack()
        }
    }

    override fun resolve(
        navTarget: NavTarget,
        componentContext: ComponentContext,
    ): Resolved =
        when (navTarget) {
            NavTarget.Root -> Resolved.Root
        }

    @Immutable
    @Serializable
    sealed interface NavTarget {
        @Serializable
        data object Root : NavTarget
    }

    sealed interface Resolved {
        data object Root : Resolved
    }
}
