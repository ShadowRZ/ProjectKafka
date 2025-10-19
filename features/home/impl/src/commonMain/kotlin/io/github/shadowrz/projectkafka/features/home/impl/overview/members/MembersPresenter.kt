package io.github.shadowrz.projectkafka.features.home.impl.overview.members

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.architecture.Presenter
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(SystemScope::class)
class MembersPresenter(
    membersStore: MembersStore,
) : Presenter<MembersState> {
    @Composable
    override fun present(): MembersState {
        val members by
            membersFlow
                .collectAsStateWithLifecycle(initialValue = Result.Loading)

        return MembersState(members = members)
    }

    private val membersFlow =
        membersStore
            .getMembers()
            .map { Result.Success(it) }
}
