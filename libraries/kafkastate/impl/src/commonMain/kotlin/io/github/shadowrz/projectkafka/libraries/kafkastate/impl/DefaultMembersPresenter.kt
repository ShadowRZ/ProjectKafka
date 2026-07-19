package io.github.shadowrz.projectkafka.libraries.kafkastate.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersPresenter
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersState
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Inject
@ContributesBinding(SystemScope::class)
class DefaultMembersPresenter(
    private val membersStore: MembersStore,
    private val coroutineDispatchers: CoroutineDispatchers,
) : MembersPresenter {
    @Composable
    override fun present(): MembersState {
        val members by
            produceState<AsyncOutcome<List<Member>>>(AsyncOutcome.Loading) {
                membersStore
                    .getMembers()
                    .map { AsyncOutcome.Success(it) }
                    .flowOn(coroutineDispatchers.computation)
                    .collect {
                        this@produceState.value = it
                    }
            }
        return MembersState(members = members)
    }
}
