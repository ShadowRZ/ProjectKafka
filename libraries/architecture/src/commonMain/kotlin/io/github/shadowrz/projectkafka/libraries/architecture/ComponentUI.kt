package io.github.shadowrz.projectkafka.libraries.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import io.github.shadowrz.hanekokoro.framework.runtime.ComponentUI
import io.github.shadowrz.hanekokoro.framework.runtime.GenericComponent
import kotlin.reflect.KClass

@OptIn(DelicateComponentUIApi::class)
@Composable
@NonRestartableComposable
inline fun <reified C : GenericComponent<*>> ComponentUI(
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
fun <C : GenericComponent<*>> ComponentUI(
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
    staticCompositionLocalOf<Map<KClass<*>, ComponentUI<*>>> {
        error("No LocalComponentUIFactories provided!")
    }
