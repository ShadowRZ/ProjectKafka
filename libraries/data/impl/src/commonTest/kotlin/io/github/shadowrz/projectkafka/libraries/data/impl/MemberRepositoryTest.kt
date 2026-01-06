package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.impl.db.SystemDatabase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import okio.Path.Companion.toPath

@OptIn(ExperimentalCoroutinesApi::class)
class MemberRepositoryTest : StringSpec() {
    private lateinit var db: SystemDatabase
    private lateinit var store: DefaultMembersStore

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
            store = DefaultMembersStore(db, coroutineDispatchers, "/".toPath())
        }

        "basic test" {
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
    }
}
