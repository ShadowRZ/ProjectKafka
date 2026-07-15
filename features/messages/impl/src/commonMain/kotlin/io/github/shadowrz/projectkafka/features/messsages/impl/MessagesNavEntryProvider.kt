package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.designsystem.navigation3.ListDetailSceneStrategy
import io.github.shadowrz.projectkafka.features.messages.api.MessagesScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesIntoSet(SystemScope::class)
class MessagesNavEntryProvider(private val presenterFactory: MessagesPresenter.Factory) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<MessagesScreen>(metadata = ListDetailSceneStrategy.detailPane()) {
            val presenter =
                remember(it.chatID) {
                    presenterFactory.create(it.chatID)
                }
            val state = presenter.present()

            MessagesUI(
                state = state,
                onBack = navigator::pop,
            )
        }
    }
}
