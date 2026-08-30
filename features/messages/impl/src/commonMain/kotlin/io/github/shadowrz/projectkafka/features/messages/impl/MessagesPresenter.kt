package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.hanekokoro.framework.runtime.retain.retainCoroutineScope
import io.github.shadowrz.projectkafka.libraries.architecture.PageableItems
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersPresenter
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AssistedInject
class MessagesPresenter(
    @Assisted private val chatID: ChatID,
    private val chatsStore: ChatsStore,
    private val membersPresenter: MembersPresenter,
    private val coroutineDispatchers: CoroutineDispatchers,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<MessagesState> {

    @Composable
    override fun present(): MessagesState {
        val lazyListState = rememberLazyListState()
        val pager =
            retain(chatID) {
                Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true)) {
                    chatsStore.getChatMessagesReversed(chatID)
                }
            }

        val lifecycleOwner = LocalLifecycleOwner.current
        val chat by
            produceState<AsyncOutcome<Chat>>(initialValue = AsyncOutcome.Loading) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    chatsStore
                        .getChatDetail(chatID)
                        .map { chat ->
                            AsyncOutcome.Success(chat)
                        }
                        .collect {
                            this@produceState.value = it
                        }
                }
            }

        val members = membersPresenter.present()

        val content = rememberRichTextState()

        var sender by
            rememberSerializable(configuration = Sender.CONFIG) {
                mutableStateOf<Sender>(Sender.Narrator)
            }

        val scope = retainCoroutineScope { coroutineDispatchers.main }
        val messagesFlow = retain(pager.flow, scope) { pager.flow.cachedIn(scope) }
        val messagesRaw = messagesFlow.collectAsLazyPagingItems()
        val messages = remember(messagesRaw) { PageableItems.AndroidX(messagesRaw) }

        return MessagesState(
            chat = chat,
            content = content,
            members = members,
            sender = sender,
            messages = messages,
            lazyListState = lazyListState,
        ) {
            when (it) {
                MessagesEvents.Send -> {
                    systemCoroutineScope.launch {
                        when (sender) {
                            is Sender.Member -> {
                                chatsStore.addMessageToChat(
                                    id = chatID,
                                    memberID = (sender as Sender.Member).memberID,
                                    content = content.toHtml(),
                                    media = null,
                                    timestamp = Clock.System.now(),
                                )
                                content.clear()
                            }
                            Sender.Narrator -> {
                                if (chat is AsyncOutcome.Success<Chat>) {
                                    chatsStore.addMessageToChat(
                                        id = chatID,
                                        memberID = (chat as AsyncOutcome.Success<Chat>).value.creatorID,
                                        content = content.toHtml(),
                                        media = null,
                                        timestamp = Clock.System.now(),
                                        narrator = true,
                                    )
                                    content.clear()
                                }
                            }
                        }
                    }
                }

                is MessagesEvents.ChangeSender -> sender = it.sender
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(chatID: ChatID): MessagesPresenter
    }
}
