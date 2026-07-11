package io.github.shadowrz.projectkafka.features.createsystem.impl

import androidx.compose.runtime.remember
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.designsystem.animation.materialSharedAxisX
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemScreen
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsComponent
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsPresenter
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsUI
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemPresenter
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemUI
import io.github.shadowrz.projectkafka.libraries.architecture.LocalNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

@Inject
@ContributesIntoSet(AppScope::class)
class CreateSystemNavEntryProvider(
    internal val createSystemPresenter: CreateSystemPresenter,
    internal val addDetailsPresenterFactory: AddDetailsPresenter.Factory,
) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry() {
        entry<CreateSystemScreen> {
            val navigator = LocalNavigator.current

            val backStack =
                rememberNavBackStack(
                    CreateSystemFlowComponent.NavTarget.CONFIG,
                    CreateSystemFlowComponent.NavTarget.CreateSystem,
                )

            fun onBack() {
                if (backStack.size <= 1) navigator.pop() else backStack.removeLastOrNull()
            }

            NavDisplay(
                backStack = backStack,
                onBack = ::onBack,
                entryProvider =
                    entryProvider {
                        entry<CreateSystemFlowComponent.NavTarget.CreateSystem> {
                            val state = createSystemPresenter.present()

                            CreateSystemUI(
                                state = state,
                                onContinue = { backStack.add(CreateSystemFlowComponent.NavTarget.AddDetails(it)) },
                            )
                        }
                        entry<CreateSystemFlowComponent.NavTarget.AddDetails> {
                            val presenter =
                                remember(it.systemName) {
                                    addDetailsPresenterFactory.create(
                                        it.systemName,
                                        object : AddDetailsComponent.Callback {
                                            override fun onFinish(id: SystemID) {
                                                // TODO
                                                // component.onFinish(id)
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
    }
}
