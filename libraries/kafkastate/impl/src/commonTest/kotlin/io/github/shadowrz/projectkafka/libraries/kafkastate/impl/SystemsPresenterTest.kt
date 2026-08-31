package io.github.shadowrz.projectkafka.libraries.kafkastate.impl

import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.data.api.SystemID
import io.github.shadowrz.projectkafka.libraries.data.api.SystemsStore
import io.github.shadowrz.projectkafka.libraries.data.test.InMemorySystemsStore
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.SystemsPresenter
import io.github.shadowrz.projectkafka.tests.utils.test
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Instant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class SystemsPresenterTest :
    FreeSpec({
        val coroutineDispatcher = UnconfinedTestDispatcher()

        beforeTest {
            Dispatchers.setMain(coroutineDispatcher)
        }

        "basic test" {
            runTest {
                val systemsStore = InMemorySystemsStore()

                presenter(systemsStore = systemsStore).test(coroutineDispatcher = coroutineDispatcher) {
                    var state = awaitItem()
                    state.systems shouldBe AsyncOutcome.Loading
                    state = awaitItem()
                    state.systems shouldBe AsyncOutcome.Success(emptyList())

                    systemsStore.createSystem(
                        name = "Demo",
                        description = "",
                        avatar = null,
                        cover = null,
                    )

                    state = awaitItem()
                    state.systems shouldBe
                        AsyncOutcome.Success(
                            listOf(
                                System(
                                    id = SystemID("system-1"),
                                    name = "Demo",
                                    description = "",
                                    avatar = null,
                                    cover = null,
                                    lastUsed = Instant.fromEpochMilliseconds(0),
                                )
                            )
                        )
                }
            }
        }
    })

private fun TestScope.presenter(systemsStore: SystemsStore): SystemsPresenter = DefaultSystemsPresenter(systemsStore = systemsStore)
