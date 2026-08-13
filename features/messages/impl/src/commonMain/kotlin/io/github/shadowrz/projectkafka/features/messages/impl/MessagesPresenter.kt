package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.hanekokoro.framework.runtime.retain.retainCoroutineScope
import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersPresenter
import kotlin.time.Clock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@AssistedInject
class MessagesPresenter(
    @Assisted private val chatID: ChatID,
    private val chatsStore: ChatsStore,
    private val membersPresenter: MembersPresenter,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<MessagesState> {

    @Composable
    override fun present(): MessagesState {
        val pager = retain {
            Pager(config = PagingConfig(pageSize = 20)) {
                chatsStore.getChatMessages(chatID)
            }
        }
        val chatsFlow = retain {
            chatsStore
                .getChatDetail(chatID)
                .map { chat ->
                    AsyncOutcome.Success(chat)
                }
                .stateIn(
                    scope = systemCoroutineScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = AsyncOutcome.Loading,
                )
        }
        val chat by chatsFlow.collectAsStateWithLifecycle()
        val members = membersPresenter.present()

        val content = rememberRichTextState()

        var sender by
            rememberSerializable(configuration = Sender.CONFIG) {
                mutableStateOf<Sender>(Sender.Narrator)
            }

        val scope = retainCoroutineScope()
        val messages = retain { pager.flow.cachedIn(scope) }

        return MessagesState(
            chat = chat,
            content = content,
            members = members,
            sender = sender,
            messages = messages,
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
