package io.github.shadowrz.projectkafka.libraries.data.impl

import androidx.paging.PagingSource
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.github.shadowrz.projectkafka.libraries.core.IDGenerator
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.api.Chat
import io.github.shadowrz.projectkafka.libraries.data.api.ChatID
import io.github.shadowrz.projectkafka.libraries.data.api.ChatMessage
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore
import io.github.shadowrz.projectkafka.libraries.data.api.MediaFile
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MessageID
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.github.shadowrz.projectkafka.libraries.data.impl.db.toDbModel
import io.github.shadowrz.projectkafka.libraries.data.impl.paging.QueryPagingSource
import io.github.shadowrz.projectkafka.libraries.data.impl.paging.RowIdAnchoredPagingSource
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.di.annotations.CacheDirectory
import io.github.shadowrz.projectkafka.libraries.di.annotations.FilesDirectory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import kotlin.time.Instant

@SingleIn(SystemScope::class)
@Inject
@ContributesBinding(SystemScope::class)
class DefaultChatsStore(
    val systemDatabase: SystemDatabase,
    val coroutineDispatchers: CoroutineDispatchers,
    @FilesDirectory private val filesDir: Path,
    @CacheDirectory private val cacheDir: Path,
    private val fileSystem: FileSystem,
) : ChatsStore {
    override fun getChats(): PagingSource<Int, Chat> =
        QueryPagingSource(
            countQuery = systemDatabase.chatQueries.chatsCount(),
            transacter = systemDatabase.chatQueries,
            context = coroutineDispatchers.io,
            queryProvider = { limit, offset ->
                systemDatabase.chatQueries.chats(limit, offset) { id, name, creatorId, avatar ->
                    Chat(
                        id = ChatID(id),
                        name = name,
                        avatar = avatar?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                        creatorID = MemberID(creatorId),
                    )
                }
            },
        )

    override fun getChatCount(): Flow<Long> =
        systemDatabase.chatQueries
            .chatsCount()
            .asFlow()
            .mapToOne(coroutineDispatchers.io)
            .distinctUntilChanged()

    override fun getChatDetail(id: ChatID): Flow<Chat> =
        systemDatabase.chatQueries
            .chatDetail(id.value) { id, name, creatorID, avatar ->
                Chat(
                    id = ChatID(id),
                    name = name,
                    avatar = avatar?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                    creatorID = MemberID(creatorID),
                )
            }.asFlow()
            .mapToOne(coroutineDispatchers.io)

    override fun getChatMessages(id: ChatID): PagingSource<Long, ChatMessage> =
        RowIdAnchoredPagingSource(
            transacter = systemDatabase.chatQueries,
            context = coroutineDispatchers.io,
            forwardQueryProvider = { anchor, limit ->
                systemDatabase.chatQueries.messagesForward(
                    id.value,
                    anchor,
                    limit,
                ) {
                    id,
                    memberId,
                    media,
                    timestamp,
                    content,
                    memberName,
                    memberDescription,
                    memberAvatar,
                    memberCover,
                    memberPreferences,
                    memberRoles,
                    memberBirth,
                    memberAdmin,
                    ->

                    ChatMessage(
                        id = MessageID(id),
                        member =
                            Member(
                                id = MemberID(memberId),
                                name = memberName,
                                description = memberDescription,
                                avatar = memberAvatar?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                                cover = memberCover?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                                preferences = memberPreferences,
                                roles = memberRoles,
                                birth = memberBirth,
                                admin = memberAdmin,
                            ),
                        content = content,
                        media = media?.let { MediaFile(it) },
                        timestamp = timestamp,
                    )
                }
            },
            backwardQueryProvider = { anchor, limit ->
                systemDatabase.chatQueries.messagesBackward(
                    id.value,
                    anchor,
                    limit,
                ) {
                    id,
                    memberId,
                    media,
                    timestamp,
                    content,
                    memberName,
                    memberDescription,
                    memberAvatar,
                    memberCover,
                    memberPreferences,
                    memberRoles,
                    memberBirth,
                    memberAdmin,
                    ->

                    ChatMessage(
                        id = MessageID(id),
                        member =
                            Member(
                                id = MemberID(memberId),
                                name = memberName,
                                description = memberDescription,
                                avatar = memberAvatar?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                                cover = memberCover?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                                preferences = memberPreferences,
                                roles = memberRoles,
                                birth = memberBirth,
                                admin = memberAdmin,
                            ),
                        content = content,
                        media = media?.let { MediaFile(it) },
                        timestamp = timestamp,
                    )
                }
            },
            rowId = { it.id.value },
        )

    override fun getSingleChatMessage(
        id: ChatID,
        messageId: MessageID,
    ): Flow<ChatMessage> =
        systemDatabase.chatQueries
            .messageItem(
                chatId = id.value,
                messageId = messageId.value,
            ) {
                id,
                memberId,
                media,
                timestamp,
                content,
                memberName,
                memberDescription,
                memberAvatar,
                memberCover,
                memberPreferences,
                memberRoles,
                memberBirth,
                memberAdmin,
                ->

                ChatMessage(
                    id = MessageID(id),
                    member =
                        Member(
                            id = MemberID(memberId),
                            name = memberName,
                            description = memberDescription,
                            avatar = memberAvatar?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                            cover = memberCover?.toAbsolute(filesDir.toString())?.let { MediaFile(it) },
                            preferences = memberPreferences,
                            roles = memberRoles,
                            birth = memberBirth,
                            admin = memberAdmin,
                        ),
                    content = content,
                    media = media?.let { MediaFile(it) },
                    timestamp = timestamp,
                )
            }.asFlow()
            .mapToOne(coroutineDispatchers.io)

    override suspend fun addChat(
        name: String?,
        avatar: MediaFile?,
        creatorID: MemberID,
    ): Chat =
        withContext(coroutineDispatchers.io) {
            with(fileSystem) {
                val model =
                    Chat(
                        id = ChatID(IDGenerator.generate()),
                        name = name,
                        avatar = avatar?.rewriteToPersisted(filesDir = filesDir, cacheDir = cacheDir)?.let { MediaFile(it) },
                        creatorID = creatorID,
                    )
                systemDatabase.chatQueries.insertChat(model.toDbModel())

                model
            }
        }

    override suspend fun addMessageToChat(
        id: ChatID,
        member: Member,
        content: String,
        media: MediaFile?,
        timestamp: Instant,
    ): ChatMessage =
        withContext(coroutineDispatchers.io) {
            with(fileSystem) {
                systemDatabase.chatQueries.transactionWithResult {
                    systemDatabase.chatQueries.insertMessageContent(content)
                    val contentId = systemDatabase.chatQueries.lastInsertRowId().executeAsOne()
                    systemDatabase.chatQueries.insertMessage(
                        chatId = id.value,
                        memberId = member.id.value,
                        contentId = contentId,
                        media = media?.rewriteToPersisted(filesDir = filesDir, cacheDir = cacheDir),
                        timestamp = timestamp,
                    )
                    val messageId = systemDatabase.chatQueries.lastInsertRowId().executeAsOne()

                    ChatMessage(
                        id = MessageID(messageId),
                        member = member,
                        content = content,
                        media = media,
                        timestamp = timestamp,
                    )
                }
            }
        }

    override suspend fun removeMessage(
        id: ChatID,
        vararg message: ChatMessage,
    ) {
        withContext(coroutineDispatchers.io) {
            val ids = message.map { it.id.value }
            systemDatabase.chatQueries.removeMessageByIDs(id.value, ids)
        }
    }

    override suspend fun editMessage(
        id: ChatID,
        messageId: MessageID,
        content: String,
        media: MediaFile?,
    ) {
        withContext(coroutineDispatchers.io) {
            with(fileSystem) {
                systemDatabase.chatQueries.editMessage(
                    content = content,
                    chatId = id.value,
                    messageId = messageId.value,
                    media = media?.rewriteToPersisted(filesDir = filesDir, cacheDir = cacheDir),
                )
            }
        }
    }
}
