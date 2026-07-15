package io.github.shadowrz.projectkafka.features.switchsystem.impl

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemScreen
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Inject
@ContributesIntoSet(AppScope::class)
class SwitchSystemNavEntryProvider(
    private val presenterFactory: SwitchSystemPresenter.Factory,
    private val systemsStore: SystemsStore,
    private val appCoroutineScope: CoroutineScope,
) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<SwitchSystemScreen> {
            val presenter = remember {
                presenterFactory.create(
                    object : SwitchSystemCallback {
                        override fun onCreateSystem() {
                            navigator.navigateTo(CreateSystemScreen)
                        }

                        override fun onSwitchSystem(id: SystemID) {
                            appCoroutineScope.launch {
                                systemsStore.updateSystemLastUsed(
                                    id,
                                    lastUsed = Clock.System.now(),
                                )
                            }
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
