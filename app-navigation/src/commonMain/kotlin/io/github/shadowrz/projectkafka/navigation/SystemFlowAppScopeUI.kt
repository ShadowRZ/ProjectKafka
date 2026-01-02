package io.github.shadowrz.projectkafka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.hanekokoro.framework.integration.ProvideHanekokoroApp

@Composable
internal fun SystemFlowAppScopeUI(
    component: SystemFlowAppScopeComponent,
    modifier: Modifier = Modifier,
) {
    ProvideHanekokoroApp(hanekokoroApp = component.hanekokoroApp) {
        HanekokoroContent(
            component = component.component,
            modifier = modifier,
        )
    }
}
