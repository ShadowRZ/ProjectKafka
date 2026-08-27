package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import kotlinx.coroutines.flow.map

@AssistedInject
class MemberProfilePresenter(
    @Assisted private val memberID: MemberID,
    private val membersStore: MembersStore,
) : Presenter<MemberProfileState> {

    @Composable
    override fun present(): MemberProfileState {
        val lifecycleOwner = LocalLifecycleOwner.current
        val member by
            produceState<AsyncOutcome<Member>>(initialValue = AsyncOutcome.Loading) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    membersStore
                        .getMember(memberID)
                        .map { member ->
                            member?.let { AsyncOutcome.Success(it) } ?: AsyncOutcome.Loading
                        }
                        .collect {
                            this@produceState.value = it
                        }
                }
            }

        return MemberProfileState(member = member)
    }

    @AssistedFactory
    fun interface Factory {
        fun create(memberID: MemberID): MemberProfilePresenter
    }
}
