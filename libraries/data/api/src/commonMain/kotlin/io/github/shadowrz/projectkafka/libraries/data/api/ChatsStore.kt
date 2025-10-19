package io.github.shadowrz.projectkafka.libraries.data.api

import androidx.paging.PagingSource
import com.eygraber.uri.Uri
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

interface ChatsStore {
    // Reading
    fun getChats(): PagingSource<Int, Chat>

    fun getChatCount(): Flow<Long>

    fun getChatDetail(id: ChatID): Flow<Chat>

    fun getChatMessages(id: ChatID): PagingSource<Long, ChatMessage>

    fun getSingleChatMessage(
        id: ChatID,
        messageId: MessageID,
    ): Flow<ChatMessage>

    // Writing
    suspend fun addChat(
        name: String?,
        avatar: Uri?,
        creatorID: MemberID,
    ): Chat

    @OptIn(ExperimentalTime::class)
    suspend fun addMessageToChat(
        id: ChatID,
        member: Member,
        content: String,
        media: Uri?,
        timestamp: Instant,
    ): ChatMessage

    suspend fun removeMessage(
        id: ChatID,
        vararg message: ChatMessage,
    )

    suspend fun editMessage(
        id: ChatID,
        messageId: MessageID,
        content: String,
        media: Uri? = null,
    )
}
