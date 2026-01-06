package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import okio.Path.Companion.toPath
import kotlin.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class ChatsRepositoryTest : StringSpec() {
    private lateinit var db: SystemDatabase
    private lateinit var store: DefaultChatsStore
    private lateinit var membersStore: DefaultMembersStore

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
            store = DefaultChatsStore(db, coroutineDispatchers, "/".toPath())
            membersStore = DefaultMembersStore(db, coroutineDispatchers, "/".toPath())
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
                val chat =
                    store.addChat(
                        name = "Test",
                        avatar = null,
                        creatorID = creator.id,
                    )
                val message =
                    store.addMessageToChat(
                        id = chat.id,
                        member = creator,
                        content = "Hello",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710630000),
                    )

                advanceUntilIdle()

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
                val chat =
                    store.addChat(
                        name = "Test",
                        avatar = null,
                        creatorID = creator.id,
                    )
                val message =
                    store.addMessageToChat(
                        id = chat.id,
                        member = creator,
                        content = "Hello",
                        media = null,
                        timestamp = Instant.fromEpochSeconds(1710630000),
                    )

                advanceUntilIdle()

                val storedMessage =
                    store
                        .getSingleChatMessage(
                            id = chat.id,
                            messageId = message.id,
                        ).first()

                storedMessage.id shouldBe message.id
                storedMessage.content shouldBe "Hello"
                storedMessage.member shouldBe message.member

                store.editMessage(
                    id = chat.id,
                    messageId = message.id,
                    content = "This message has been edited",
                    media = message.media,
                )

                advanceUntilIdle()

                val newMessage =
                    store
                        .getSingleChatMessage(
                            id = chat.id,
                            messageId = message.id,
                        ).first()

                advanceUntilIdle()

                newMessage.id shouldBe message.id
                newMessage.content shouldBe "This message has been edited"
                newMessage.member shouldBe message.member
            }
        }
    }
}
