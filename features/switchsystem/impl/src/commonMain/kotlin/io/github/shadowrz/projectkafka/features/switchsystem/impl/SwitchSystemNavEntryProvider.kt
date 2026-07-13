package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.runtime.remember
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemScreen
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemScreen
import io.github.shadowrz.projectkafka.libraries.architecture.LocalNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID

@Inject
@ContributesIntoSet(AppScope::class)
class SwitchSystemNavEntryProvider(private val presenterFactory: SwitchSystemPresenter.Factory) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry() {
        entry<SwitchSystemScreen> {
            val navigator = LocalNavigator.current
            val presenter = remember {
                presenterFactory.create(
                    object : SwitchSystemCallback {
                        override fun onCreateSystem() {
                            navigator.navigateTo(CreateSystemScreen)
                        }

                        override fun onSwitchSystem(id: SystemID) {
                            // TODO
                        }
                    }
                )
            }
            val state = presenter.present()

            SwitchSystemUI(
                state = state,
                onBack = navigator::pop,
            )
        }
    }
}
