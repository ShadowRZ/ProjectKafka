package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.impl.db.GlobalDatabase
import io.github.shadowrz.projectkafka.libraries.uniqueid.UniqueID
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

@OptIn(ExperimentalCoroutinesApi::class)
class SystemRepositoryTest : FreeSpec() {
    private lateinit var db: GlobalDatabase
    private lateinit var store: DefaultSystemsStore
    private val fileSystem = FakeFileSystem()

    init {
        beforeTest {
            val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
            GlobalDatabase.Schema.create(driver)
            db = globalDatabase(driver)
            val coroutineDispatchers =
                CoroutineDispatchers(
                    io = UnconfinedTestDispatcher(),
                    computation = UnconfinedTestDispatcher(),
                    main = UnconfinedTestDispatcher(),
                )
            store =
                DefaultSystemsStore(
                    db,
                    coroutineDispatchers,
                    "/files".toPath(),
                    "/cache".toPath(),
                    fileSystem,
                    UniqueID.IncrementingID("system-"),
                )
        }

        "basic test" {
            runTest {
                val id =
                    store.createSystem(
                        name = "???? System",
                        avatar = null,
                        cover = null,
                        description = "(Description)",
                    )

                advanceUntilIdle()

                val system = store.getSystem(id)

                system.name shouldBe "???? System"
                system.description shouldBe "(Description)"
                system.avatar.shouldBeNull()
                system.cover.shouldBeNull()
            }
        }
    }
}
