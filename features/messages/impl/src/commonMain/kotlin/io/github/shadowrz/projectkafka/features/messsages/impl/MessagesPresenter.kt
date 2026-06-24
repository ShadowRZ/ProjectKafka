package io.github.shadowrz.projectkafka.features.messsages.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.retain.retain
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.hanekokoro.framework.runtime.retain.retainCoroutineScope
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AssistedInject
class MessagesPresenter(
    @Assisted private val chatID: ChatID,
    private val chatsStore: ChatsStore,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<MessagesState> {

    private val chatsFlow =
        chatsStore
            .getChatDetail(chatID)
            .map { chat ->
                Result.Success(chat)
            }
            .stateIn(
                scope = systemCoroutineScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Result.Loading,
            )

    @Composable
    override fun present(): MessagesState {
        val pager = retain {
            Pager(config = PagingConfig(pageSize = 20)) {
                chatsStore.getChatMessages(chatID)
            }
        }
        val chat by chatsFlow.collectAsStateWithLifecycle()

        val scope = retainCoroutineScope()
        val messages = retain { pager.flow.cachedIn(scope) }

        return MessagesState(
            chat = chat,
            messages = messages,
        )
    }

    @AssistedFactory
    fun interface Factory {
        fun create(chatID: ChatID): MessagesPresenter
    }
}
