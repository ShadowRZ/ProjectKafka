package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.licenses.api.LicensesScreen
import io.github.shadowrz.projectkafka.libraries.architecture.LocalNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider

@Inject
@ContributesIntoSet(AppScope::class)
class LicensesNavEntryProvider(private val presenter: LicensesPresenter) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry() {
        entry<LicensesScreen> {
            val state = presenter.present()
            val navigator = LocalNavigator.current

            LicensesUI(
                state = state,
                onBack = { navigator.pop() },
            )
        }
    }
}
