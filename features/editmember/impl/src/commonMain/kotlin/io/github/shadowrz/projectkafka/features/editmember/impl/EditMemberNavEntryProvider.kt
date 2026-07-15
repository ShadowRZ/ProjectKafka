package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.result.LocalResultEventBus
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.designsystem.LoadingIndicator
import io.github.shadowrz.projectkafka.features.editmember.api.AddMemberScreen
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.resultevents.ResultEvents
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_edit_member
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import org.jetbrains.compose.resources.stringResource

@Inject
@ContributesIntoSet(SystemScope::class)
class EditMemberNavEntryProvider(
    private val addMemberPresenterFactory: AddMemberPresenter.Factory,
    private val editMemberPresenterFactory: EditMemberPresenter.Factory,
) : NavEntryProvider {
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<AddMemberScreen> {
            val presenter = remember {
                addMemberPresenterFactory.create(
                    object : AddMemberCallback {
                        override fun onFinish() {
                            navigator.pop()
                        }
                    }
                )
            }
            val state = presenter.present()

            MemberFieldEditUI(
                title = stringResource(CommonStrings.common_new_member),
                state = state,
            )
        }
        entry<EditMemberScreen> {
            val resultEventBus = LocalResultEventBus.current

            val presenter = remember {
                editMemberPresenterFactory.create(
                    memberID = it.memberID,
                    callback =
                        object : EditMemberCallback {
                            override fun onFinish() {
                                navigator.pop()
                            }
                        },
                )
            }
            val state = presenter.present()

            when (val state = presenter.present()) {
                AsyncOutcome.Loading -> {
                    LoadingIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
                }

                is AsyncOutcome.Success<MemberFieldEditState> -> {
                    MemberFieldEditUI(
                        title = stringResource(CommonStrings.common_edit_member),
                        state = state.value,
                        supportDeleteMember = true,
                        onDeleteMember = {
                            resultEventBus.sendResult(ResultEvents.MemberDeleted(it.memberID))
                        },
                    )
                }
            }
        }
    }
}
