package io.github.shadowrz.projectkafka

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.sharedelements.SharedElementTransitionLayout
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainUI(
    component: MainComponent,
    modifier: Modifier = Modifier,
) {
    SharedElementTransitionLayout {
        ComponentUI(
            component = component.rootFlowComponent,
            modifier = modifier,
        )
    }
}
