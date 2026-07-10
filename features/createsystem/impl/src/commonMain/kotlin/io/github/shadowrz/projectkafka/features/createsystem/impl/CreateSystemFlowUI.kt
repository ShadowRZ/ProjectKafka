package io.github.shadowrz.projectkafka.features.createsystem.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.arkivanov.decompose.ExperimentalDecomposeApi
import dev.zacsweers.metro.AppScope
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.designsystem.animation.materialSharedAxisX
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsComponent
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsUI
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemUI
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

@OptIn(ExperimentalDecomposeApi::class)
@Composable
@HanekokoroInject.ContributesRenderer(AppScope::class)
internal fun CreateSystemFlowUI(
    component: CreateSystemFlowComponent,
    modifier: Modifier = Modifier,
) {
    val backStack =
        rememberNavBackStack(
            CreateSystemFlowComponent.NavTarget.CONFIG,
            CreateSystemFlowComponent.NavTarget.CreateSystem,
        )

    fun onBack() {
        if (backStack.size <= 1) component.onBack() else backStack.removeLastOrNull()
    }

    NavDisplay(
        backStack = backStack,
        onBack = ::onBack,
        modifier = modifier,
        entryProvider =
            entryProvider {
                entry<CreateSystemFlowComponent.NavTarget.CreateSystem> {
                    val state = component.createSystemPresenter.present()

                    CreateSystemUI(
                        state = state,
                        onContinue = { backStack.add(CreateSystemFlowComponent.NavTarget.AddDetails(it)) },
                    )
                }
                entry<CreateSystemFlowComponent.NavTarget.AddDetails> {
                    val presenter =
                        remember(it.systemName) {
                            component.addDetailsPresenterFactory.create(
                                it.systemName,
                                object : AddDetailsComponent.Callback {
                                    override fun onFinish(id: SystemID) {
                                        component.onFinish(id)
                                    }
                                },
                            )
                        }
                    val state = presenter.present()
                    AddDetailsUI(
                        state = state,
                        onBack = ::onBack,
                    )
                }
            },
        transitionSpec = { materialSharedAxisX(forward = true) },
        popTransitionSpec = { materialSharedAxisX(forward = false) },
        predictivePopTransitionSpec = { materialSharedAxisX(forward = false) },
    )
}
