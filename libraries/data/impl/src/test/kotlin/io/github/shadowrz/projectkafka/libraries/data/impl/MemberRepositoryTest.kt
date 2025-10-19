package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.sqldelight.logs.LogSqliteDriver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.InstantAdapter
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.LocalDateAdapter
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.UriAdapter
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.Chat
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.Member
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.Message
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.SystemDatabase
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test

class MemberRepositoryTest {
    private lateinit var db: SystemDatabase
    private lateinit var store: DefaultMembersStore

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setup() {
        val driver =
            LogSqliteDriver(JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)) {
                println(it)
            }
        SystemDatabase.Schema.create(driver)
        db =
            SystemDatabase(
                driver = driver,
                chatAdapter =
                    Chat.Adapter(
                        avatarAdapter = UriAdapter,
                    ),
                messageAdapter =
                    Message.Adapter(
                        mediaAdapter = UriAdapter,
                        timestampAdapter = InstantAdapter,
                    ),
                memberAdapter =
                    Member.Adapter(
                        avatarAdapter = UriAdapter,
                        coverAdapter = UriAdapter,
                        birthAdapter = LocalDateAdapter,
                    ),
            )
        val coroutineDispatchers =
            CoroutineDispatchers(
                io = UnconfinedTestDispatcher(),
                computation = UnconfinedTestDispatcher(),
                main = UnconfinedTestDispatcher(),
            )
        store = DefaultMembersStore(db, coroutineDispatchers)
    }

    @Test
    fun `basic test`() =
        runTest {
            val system =
                store.createMember(
                    name = "Futaba",
                    description = "(Description)",
                    avatar = null,
                    cover = null,
                    preferences = "(Preferences)",
                    roles = "(Roles)",
                    birth = LocalDate(2024, 1, 1),
                    admin = false,
                )

            system.name shouldBe "Futaba"
            system.description shouldBe "(Description)"
            system.avatar.shouldBeNull()
            system.cover.shouldBeNull()
            system.preferences shouldBe "(Preferences)"
            system.roles shouldBe "(Roles)"
            system.birth shouldBe LocalDate(2024, 1, 1)
            system.admin.shouldBeFalse()
        }
}
