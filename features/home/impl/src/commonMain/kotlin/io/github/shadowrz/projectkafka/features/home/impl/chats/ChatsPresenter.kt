package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.hanekokoro.framework.runtime.retain.retainCoroutineScope
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore

const val PAGES = 20

@Inject
class ChatsPresenter(private val chatsStore: ChatsStore) : Presenter<ChatsState> {
    @Composable
    override fun present(): ChatsState {
        val scope = retainCoroutineScope()
        val chats = retain {
            Pager(PagingConfig(pageSize = PAGES)) {
                    chatsStore.getChats()
                }
                .flow
                .cachedIn(scope)
        }
        var chatsType by rememberSaveable {
            mutableStateOf<ChatsType?>(null)
        }

        return ChatsState(
            chats = chats,
            chatsType = chatsType,
        ) {
            when (it) {
                is ChatsEvents.ChangeChatsType -> {
                    chatsType = it.chatsType
                }
            }
        }
    }
}
