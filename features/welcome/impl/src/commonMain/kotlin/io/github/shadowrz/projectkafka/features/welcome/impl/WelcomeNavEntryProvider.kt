package io.github.shadowrz.projectkafka.features.welcome.impl

import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.quickstart.api.QuickStartScreen
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator

@Inject
@ContributesIntoSet(AppScope::class)
class WelcomeNavEntryProvider : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<WelcomeScreen> {
            WelcomeUI(onQuickStart = { navigator.navigateTo(QuickStartScreen) })
        }
    }
}
