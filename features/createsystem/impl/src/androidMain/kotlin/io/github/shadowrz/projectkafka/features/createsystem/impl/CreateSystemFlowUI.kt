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
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsUI
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemUI
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.components.predictiveback.defaultPredictiveBackParams

@SingleIn(AppScope::class)
@Inject
@ContributesIntoMap(
    AppScope::class,
    binding = binding<ComponentUI<*>>(),
)
@ComponentKey(CreateSystemFlowComponent::class)
class CreateSystemFlowUI : ComponentUI<CreateSystemFlowComponent> {
    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun Content(
        component: CreateSystemFlowComponent,
        modifier: Modifier,
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
}
