package io.github.shadowrz.projectkafka.libraries.data.impl

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.github.shadowrz.projectkafka.libraries.uniqueid.UniqueID
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import kotlin.time.Instant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

@OptIn(ExperimentalCoroutinesApi::class)
class ChatsRepositoryTest : FreeSpec() {
    private lateinit var db: SystemDatabase
    private lateinit var store: DefaultChatsStore
    private lateinit var membersStore: DefaultMembersStore
    private val fileSystem = FakeFileSystem()

    init {
        beforeTest {
            val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            SystemDatabase.Schema.create(driver)
            db = systemDatabase(driver)
            val coroutineDispatchers =
                CoroutineDispatchers(
                    io = UnconfinedTestDispatcher(),
                    computation = UnconfinedTestDispatcher(),
                    main = UnconfinedTestDispatcher(),
                )
            store =
                DefaultChatsStore(
                    db,
                    coroutineDispatchers,
                    "/files".toPath(),
                    "/cache".toPath(),
                    fileSystem,
                    UniqueID.IncrementingID("chats-"),
                )
            membersStore =
                DefaultMembersStore(
                    db,
                    coroutineDispatchers,
                    "/files".toPath(),
                    "/cache".toPath(),
                    fileSystem,
                    UniqueID.IncrementingID("members-"),
                )
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        "basic test" {
            runTest {
                val creator =
                    membersStore.createMember(
                        name = "Futaba",
                        description = "(Description)",
                        avatar = null,
                        cover = null,
                        preferences = "(Preferences)",
                        roles = "(Roles)",
                        birth = LocalDate(2024, 1, 1),
                        admin = false,
                    )
                val chatID =
                    store.addChat(
                        name = "Test",
                        avatar = null,
                        creatorID = creator.id,
                    )

                val messageID =
                    store.addMessageToChat(
                        id = chatID,
                        memberID = creator.id,
                        content = "Hello",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710630000),
                    )

                val chat = store.getChatDetail(chatID).first()

                advanceUntilIdle()

                val message = store.getSingleChatMessage(id = chatID, messageId = messageID).first()

                chat.name shouldBe "Test"
                message.content shouldBe "Hello"
                message.member shouldBe creator
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        "editing" {
            runTest {
                val creator =
                    membersStore.createMember(
                        name = "Futaba",
                        description = "(Description)",
                        avatar = null,
                        cover = null,
                        preferences = "(Preferences)",
                        roles = "(Roles)",
                        birth = LocalDate(2024, 1, 1),
                        admin = false,
                    )
                val chatID =
                    store.addChat(
                        name = "Test",
                        avatar = null,
                        creatorID = creator.id,
                    )
                val messageID =
                    store.addMessageToChat(
                        id = chatID,
                        memberID = creator.id,
                        content = "Hello",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710630000),
                    )

                advanceUntilIdle()

                val storedMessage =
                    store
                        .getSingleChatMessage(
                            id = chatID,
                            messageId = messageID,
                        )
                        .first()

                storedMessage.id shouldBe messageID
                storedMessage.content shouldBe "Hello"
                storedMessage.member shouldBe creator

                store.editMessage(
                    id = chatID,
                    messageId = messageID,
                    content = "This message has been edited",
                    media = null,
                )

                advanceUntilIdle()

                val newMessage =
                    store
                        .getSingleChatMessage(
                            id = chatID,
                            messageId = messageID,
                        )
                        .first()

                advanceUntilIdle()

                newMessage.id shouldBe messageID
                newMessage.content shouldBe "This message has been edited"
                newMessage.member shouldBe creator
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        "pagination" {
            runTest {
                val creator =
                    membersStore.createMember(
                        name = "Futaba",
                        description = "(Description)",
                        avatar = null,
                        cover = null,
                        preferences = "(Preferences)",
                        roles = "(Roles)",
                        birth = LocalDate(2024, 1, 1),
                        admin = false,
                    )
                val chatID =
                    store.addChat(
                        name = "Test",
                        avatar = null,
                        creatorID = creator.id,
                    )
                val message1ID =
                    store.addMessageToChat(
                        id = chatID,
                        memberID = creator.id,
                        content = "Hello",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710630000),
                    )

                advanceUntilIdle()

                val message2ID =
                    store.addMessageToChat(
                        id = chatID,
                        memberID = creator.id,
                        content = "Hello Again",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710640000),
                    )

                advanceUntilIdle()

                val message1 = store.getSingleChatMessage(chatID, message1ID).first()
                val message2 = store.getSingleChatMessage(chatID, message2ID).first()

                val source = store.getChatMessages(chatID)

                val pager = TestPager(PagingConfig(pageSize = 20), source)
                val result = pager.refresh() as PagingSource.LoadResult.Page

                result.data shouldBeEqual listOf(message1, message2)
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        "reversed pagination" {
            runTest {
                val creator =
                    membersStore.createMember(
                        name = "Futaba",
                        description = "(Description)",
                        avatar = null,
                        cover = null,
                        preferences = "(Preferences)",
                        roles = "(Roles)",
                        birth = LocalDate(2024, 1, 1),
                        admin = false,
                    )
                val chatID =
                    store.addChat(
                        name = "Test",
                        avatar = null,
                        creatorID = creator.id,
                    )
                val message1ID =
                    store.addMessageToChat(
                        id = chatID,
                        memberID = creator.id,
                        content = "Hello",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710630000),
                    )

                advanceUntilIdle()

                val message2ID =
                    store.addMessageToChat(
                        id = chatID,
                        memberID = creator.id,
                        content = "Hello Again",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710640000),
                    )

                advanceUntilIdle()

                val message1 = store.getSingleChatMessage(chatID, message1ID).first()
                val message2 = store.getSingleChatMessage(chatID, message2ID).first()

                val source = store.getChatMessagesReversed(chatID)

                val pager = TestPager(PagingConfig(pageSize = 20), source)
                val result = pager.refresh() as PagingSource.LoadResult.Page

                result.data shouldBeEqual listOf(message2, message1)
            }
        }
    }
}
