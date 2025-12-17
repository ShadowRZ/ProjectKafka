package io.github.shadowrz.projectkafka.features.createsystem.impl

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.experimental.stack.animation.stackAnimation
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsUI
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemUI
import io.github.shadowrz.projectkafka.libraries.components.predictiveback.defaultPredictiveBackParams

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun CreateSystemFlowUI(
    component: CreateSystemFlowComponent,
    modifier: Modifier = Modifier,
) {
    ChildStack(
        stack = component.childStack,
        modifier = modifier,
        animation =
            stackAnimation(
                animator = fade() + slide(),
                predictiveBackParams = {
                    defaultPredictiveBackParams(
                        enabled = Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM,
                        backHandler = component.backHandler,
                        onBack = component::onBack,
                    )
                },
            ),
    ) {
        when (val child = it.instance) {
            is CreateSystemFlowComponent.Resolved.CreateSystem -> {
                val state = child.component.presenter.present()

                CreateSystemUI(
                    state = state,
                    onContinue = child.component.callback::onContinue,
                )
            }

            is CreateSystemFlowComponent.Resolved.AddDetails -> {
                val state = child.component.presenter.present()

                AddDetailsUI(
                    state = state,
                )
            }
        }
    }
}
