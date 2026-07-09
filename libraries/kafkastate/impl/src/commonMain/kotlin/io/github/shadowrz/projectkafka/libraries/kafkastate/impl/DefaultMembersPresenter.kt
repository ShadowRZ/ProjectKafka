package io.github.shadowrz.projectkafka.libraries.kafkastate.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersPresenter
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(SystemScope::class)
class DefaultMembersPresenter(
    membersStore: MembersStore,
    coroutineDispatchers: CoroutineDispatchers,
) : MembersPresenter {
    @Composable
    override fun present(): MembersState {
        val members by membersFlow.collectAsState(initial = AsyncOutcome.Loading)

        return MembersState(members = members)
    }

    private val membersFlow = membersStore.getMembers().map { AsyncOutcome.Success(it) }.flowOn(coroutineDispatchers.computation)
}
