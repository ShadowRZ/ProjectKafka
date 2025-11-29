package io.github.shadowrz.projectkafka.libraries.data.impl

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.sqldelight.logs.LogSqliteDriver
import io.github.shadowrz.projectkafka.libraries.core.coroutine.CoroutineDispatchers
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.InstantAdapter
import io.github.shadowrz.projectkafka.libraries.data.impl.db.adapters.UriAdapter
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.GlobalDatabase
import io.github.shadowrz.projectkakfa.libraries.data.impl.db.System
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.Path.Companion.toPath

@OptIn(ExperimentalCoroutinesApi::class)
class SystemRepositoryTest : StringSpec() {
    private lateinit var db: GlobalDatabase
    private lateinit var store: DefaultSystemsStore

    init {
        beforeTest {
            val driver =
                LogSqliteDriver(JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)) {
                    println(it)
                }
            GlobalDatabase.Schema.create(driver)
            db =
                GlobalDatabase(
                    driver = driver,
                    systemAdapter =
                        System.Adapter(
                            avatarAdapter = UriAdapter,
                            coverAdapter = UriAdapter,
                            lastUsedAdapter = InstantAdapter,
                        ),
                )
            val coroutineDispatchers =
                CoroutineDispatchers(
                    io = UnconfinedTestDispatcher(),
                    computation = UnconfinedTestDispatcher(),
                    main = UnconfinedTestDispatcher(),
                )
            store = DefaultSystemsStore(db, coroutineDispatchers, "/".toPath())
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
