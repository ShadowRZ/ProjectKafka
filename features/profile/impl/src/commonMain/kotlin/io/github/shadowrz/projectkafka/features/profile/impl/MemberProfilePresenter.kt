package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AssistedInject
class MemberProfilePresenter(
    @Assisted private val memberID: MemberID,
    private val membersStore: MembersStore,
    @ForScope(SystemScope::class) systemCoroutineScope: CoroutineScope,
) : Presenter<MemberProfileState> {
    val memberStateFlow =
        membersStore
            .getMember(memberID)
            .map { member ->
                member?.let { Result.Success(it) } ?: Result.Loading
            }.stateIn(
                scope = systemCoroutineScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Result.Loading,
            )

    @Composable
    override fun present(): MemberProfileState {
        val member by memberStateFlow.collectAsStateWithLifecycle()

        return MemberProfileState(
            member = member,
        )
    }

    @AssistedFactory
    fun interface Factory {
        fun create(memberID: MemberID): MemberProfilePresenter
    }
}
