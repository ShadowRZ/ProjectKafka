package io.github.shadowrz.projectkafka.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import io.github.shadowrz.hanekokoro.framework.integration.HanekokoroContent
import io.github.shadowrz.projectkafka.designsystem.ChildStack
import io.github.shadowrz.projectkafka.designsystem.MobileLockOrientation
import io.github.shadowrz.projectkafka.designsystem.ScreenOrientation

@OptIn(ExperimentalDecomposeApi::class)
@Composable
internal fun NoSystemFlowUI(
    component: NoSystemFlowComponent,
    modifier: Modifier = Modifier,
) {
    ChildStack(
        modifier = modifier,
        stack = component.childStack,
        backHandler = component.backHandler,
        onBack = component::onBack,
    ) {
        HanekokoroContent(
            component = it.instance,
        )
    }

    MobileLockOrientation(orientation = ScreenOrientation.PORTRAIT)
}
