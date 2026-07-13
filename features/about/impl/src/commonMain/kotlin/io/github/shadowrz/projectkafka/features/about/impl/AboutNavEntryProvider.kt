package io.github.shadowrz.projectkafka.features.about.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.about.api.AboutScreen
import io.github.shadowrz.projectkafka.features.licenses.api.LicensesScreen
import io.github.shadowrz.projectkafka.libraries.architecture.LocalNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider

@Inject
@ContributesIntoSet(AppScope::class)
class AboutNavEntryProvider(private val presenter: AboutPresenter) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry() {
        entry<AboutScreen> {
            val state = presenter.present()
            val navigator = LocalNavigator.current

            AboutUI(
                state = state,
                onBack = { navigator.pop() },
                onLicenses = { navigator.navigateTo(LicensesScreen) },
            )
        }
    }
}
