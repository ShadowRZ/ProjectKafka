package io.github.shadowrz.projectkafka

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.sharedelements.SharedElementTransitionLayout
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun MainUI(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    SharedElementTransitionLayout {
        HanekokoroContent(
            component = component.rootFlowComponent,
            modifier = modifier,
        )
    }
}
