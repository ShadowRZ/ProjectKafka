package io.github.shadowrz.projectkafka.features.preferences.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageScreen
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesScreen
import io.github.shadowrz.projectkafka.features.preferences.impl.root.PreferencesRootState
import io.github.shadowrz.projectkafka.features.preferences.impl.root.PreferencesRootUI
import io.github.shadowrz.projectkafka.libraries.architecture.LocalNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider

@Inject
@ContributesIntoSet(AppScope::class)
class PreferencesNavEntryPoint(private val rootPresenter: Presenter<PreferencesRootState>) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry() {
        entry<PreferencesScreen> {
            val backStack =
                rememberNavBackStack(
                    PreferencesComponent.NavTarget.CONFIG,
                    PreferencesComponent.NavTarget.Root,
                )
            val navigator = LocalNavigator.current

            fun onBack() {
                if (backStack.size <= 1) navigator.pop() else backStack.removeLastOrNull()
            }

            NavDisplay(
                backStack = backStack,
                onBack = ::onBack,
                entryProvider =
                    entryProvider {
                        entry<PreferencesComponent.NavTarget.Root> {
                            val state = rootPresenter.present()

                            PreferencesRootUI(
                                state = state,
                                onBack = ::onBack,
                                onDataManage = { navigator.navigateTo(DataManageScreen) },
                            )
                        }
                    },
            )
        }
    }
}
