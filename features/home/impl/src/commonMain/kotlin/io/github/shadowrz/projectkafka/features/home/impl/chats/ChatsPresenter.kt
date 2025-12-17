package io.github.shadowrz.projectkafka.features.home.impl.chats

import androidx.paging.PagingData
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import kotlinx.coroutines.flow.Flow

expect class ChatsPresenter : Presenter<ChatsState> {
    fun interface Factory {
        fun create(chats: Flow<PagingData<Chat>>): ChatsPresenter
    }
}
