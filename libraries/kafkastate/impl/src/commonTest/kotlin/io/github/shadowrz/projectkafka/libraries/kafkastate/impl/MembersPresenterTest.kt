package io.github.shadowrz.projectkafka.libraries.kafkastate.impl

import io.github.shadowrz.projectkafka.libraries.core.AsyncOutcome
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.data.api.MembersStore
import io.github.shadowrz.projectkafka.libraries.data.test.InMemoryMembersStore
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersPresenter
import io.github.shadowrz.projectkafka.tests.utils.test
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MembersPresenterTest :
    FreeSpec({
        val coroutineDispatcher = UnconfinedTestDispatcher()

        beforeTest {
            Dispatchers.setMain(coroutineDispatcher)
        }

        "basic test" {
            runTest {
                val membersStore = InMemoryMembersStore()

                presenter(membersStore = membersStore).test(coroutineDispatcher = coroutineDispatcher) {
                    var state = awaitItem()
                    state.members shouldBe AsyncOutcome.Loading
                    state = awaitItem()
                    state.members shouldBe AsyncOutcome.Success(emptyList())

                    membersStore.createMember(
                        name = "Hello",
                        description = "",
                        avatar = null,
                        cover = null,
                        preferences = null,
                        roles = null,
                        birth = null,
                        admin = false,
                        fields = null,
                    )

                    state = awaitItem()
                    state.members shouldBe
                        AsyncOutcome.Success(
                            listOf(
                                Member(
                                    id = MemberID("member-1"),
                                    name = "Hello",
                                    description = "",
                                    avatar = null,
                                    cover = null,
                                    preferences = null,
                                    roles = null,
                                    birth = null,
                                    admin = false,
                                    fields = emptyMap(),
                                )
                            )
                        )
                }
            }
        }
    })

private fun TestScope.presenter(membersStore: MembersStore): MembersPresenter = DefaultMembersPresenter(membersStore = membersStore)
