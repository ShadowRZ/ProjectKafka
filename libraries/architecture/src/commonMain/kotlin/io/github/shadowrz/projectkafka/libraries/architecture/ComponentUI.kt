package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import dev.zacsweers.metro.Multibinds
import kotlin.reflect.KClass

fun interface ComponentUI<C : Component> {
    @Composable
    fun Content(
        component: C,
        modifier: Modifier,
    )

    interface Factories {
        @Multibinds
        fun uiFactories(): Map<KClass<out Component>, ComponentUI<*>>
    }
}

@OptIn(DelicateComponentUIApi::class)
@Composable
@NonRestartableComposable
inline fun <reified C : Component> ComponentUI(
    component: C,
    modifier: Modifier = Modifier,
) {
    ComponentUI(
        component = component,
        kclass = component::class,
        modifier = modifier,
    )
}

@Suppress("UNCHECKED_CAST")
@DelicateComponentUIApi
@Composable
fun <C : Component> ComponentUI(
    component: Any,
    kclass: KClass<C>,
    modifier: Modifier = Modifier,
) {
    val uiFactories = LocalComponentUIFactories.current

    val ui =
        requireNotNull(uiFactories[kclass] as? ComponentUI<C>) {
            "Didn't found the UI for ${kclass.qualifiedName}, is it injected properly?"
        }

    ui.Content(
        component = component as C,
        modifier = modifier,
    )
}

val LocalComponentUIFactories =
    staticCompositionLocalOf<Map<KClass<out Component>, ComponentUI<*>>> {
        error("No LocalComponentUIFactories provided!")
    }
