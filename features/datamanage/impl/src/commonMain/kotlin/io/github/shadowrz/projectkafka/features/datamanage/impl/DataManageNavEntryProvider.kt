package io.github.shadowrz.projectkafka.features.datamanage.impl

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator

@Inject
@ContributesIntoSet(AppScope::class)
class DataManageNavEntryProvider(private val presenter: Presenter<DataManageState>) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<DataManageScreen> {
            val state = presenter.present()

            DataManageUI(
                state = state,
                onBack = { navigator.pop() },
            )
        }
    }
}
