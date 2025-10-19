package io.github.shadowrz.projectkafka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI

@Composable
internal fun SystemFlowAppScopeUI(
    component: SystemFlowAppScopeComponent,
    modifier: Modifier = Modifier,
) {
    ProvideComponentUIFactories(uiFactories = component.uiFactories) {
        ComponentUI(
            component = component.component,
            modifier = modifier,
        )
    }
}
