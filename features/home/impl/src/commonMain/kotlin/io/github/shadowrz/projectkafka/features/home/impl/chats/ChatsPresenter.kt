package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.paging.PagingData
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import kotlinx.coroutines.flow.Flow

@AssistedInject
class ChatsPresenter(
    @Assisted private val chats: Flow<PagingData<Chat>>,
) : Presenter<ChatsState> {
    @Composable
    override fun present(): ChatsState {
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

    @AssistedFactory
    fun interface Factory {
        fun create(chats: Flow<PagingData<Chat>>): ChatsPresenter
    }
}
