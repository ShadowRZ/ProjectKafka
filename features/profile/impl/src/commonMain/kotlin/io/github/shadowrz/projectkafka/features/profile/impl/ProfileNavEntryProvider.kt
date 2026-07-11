package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.runtime.remember
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberScreen
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileScreen
import io.github.shadowrz.projectkafka.libraries.architecture.LocalNavigator
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesIntoSet(SystemScope::class)
class ProfileNavEntryProvider(private val memberProfilePresenterFactory: MemberProfilePresenter.Factory) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry() {
        entry<MemberProfileScreen> {
            val navigator = LocalNavigator.current
            val presenter =
                remember(it.memberID) {
                    memberProfilePresenterFactory.create(it.memberID)
                }

            val state = presenter.present()

            MemberProfileUI(
                state = state,
                onBack = navigator::pop,
                onEdit = { navigator.navigateTo(EditMemberScreen(it.memberID)) },
            )
        }
    }
}
