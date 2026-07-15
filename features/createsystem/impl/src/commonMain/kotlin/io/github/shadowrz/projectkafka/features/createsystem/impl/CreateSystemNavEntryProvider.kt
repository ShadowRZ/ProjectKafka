package io.github.shadowrz.projectkafka.features.createsystem.impl

import androidx.compose.animation.SharedTransitionScope
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
import io.github.shadowrz.projectkafka.designsystem.animation.rememberSlideDistance
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemScreen
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsPresenter
import io.github.shadowrz.projectkafka.features.createsystem.impl.adddetails.AddDetailsUI
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemPresenter
import io.github.shadowrz.projectkafka.features.createsystem.impl.createsystem.CreateSystemUI
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator

@Inject
@ContributesIntoSet(AppScope::class)
class CreateSystemNavEntryProvider(
    internal val createSystemPresenter: CreateSystemPresenter,
    internal val addDetailsPresenterFactory: AddDetailsPresenter.Factory,
) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<CreateSystemScreen> {
            val slideDistance = rememberSlideDistance()
            val backStack =
                rememberNavBackStack(
                    CreateSystemNavTarget.CONFIG,
                    CreateSystemNavTarget.CreateSystem,
                )

            fun onBack() {
                if (backStack.size <= 1) navigator.pop() else backStack.removeLastOrNull()
            }

            NavDisplay(
                backStack = backStack,
                onBack = ::onBack,
                entryProvider =
                    entryProvider {
                        entry<CreateSystemNavTarget.CreateSystem> {
                            val state = createSystemPresenter.present()

                            CreateSystemUI(
                                state = state,
                                onContinue = { backStack.add(CreateSystemNavTarget.AddDetails(it)) },
                            )
                        }
                        entry<CreateSystemNavTarget.AddDetails> {
                            val presenter =
                                remember(it.systemName) {
                                    addDetailsPresenterFactory.create(it.systemName)
                                }
                            val state = presenter.present()

                            AddDetailsUI(
                                state = state,
                                onBack = ::onBack,
                            )
                        }
                    },
                transitionSpec = { materialSharedAxisX(forward = true, slideDistance = slideDistance) },
                popTransitionSpec = { materialSharedAxisX(forward = false, slideDistance = slideDistance) },
                predictivePopTransitionSpec = { materialSharedAxisX(forward = false, slideDistance = slideDistance) },
            )
        }
    }
}
