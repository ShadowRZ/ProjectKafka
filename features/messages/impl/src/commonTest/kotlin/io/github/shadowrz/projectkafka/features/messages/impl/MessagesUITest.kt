package io.github.shadowrz.projectkafka.features.messages.impl

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertNeverCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallback
import io.github.shadowrz.projectkafka.tests.utils.callback.AssertableCallbackWithParam
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalled
import io.github.shadowrz.projectkafka.tests.utils.callback.shouldBeCalledWith
import io.github.shadowrz.projectkafka.tests.utils.uitest.pressBack
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalTestApi::class)
class MessagesUITest :
    FreeSpec({
        "chat loading" -
            {
                "clicking on back invokes onBack callback" {
                    runComposeUiTest {
                        val callback = AssertableCallback()

                        setMessagesView(
                            state = aLoadingMessagesState(),
                            onBack = callback,
                        )

                        pressBack()
                        callback.shouldBeCalled()
                    }
                }
            }
        "chat loaded" -
            {
                "clicking on back invokes onBack callback" {
                    runComposeUiTest {
                        val callback = AssertableCallback()

                        setMessagesView(
                            state = aEmptyMessagesState(),
                            onBack = callback,
                        )

                        pressBack()
                        callback.shouldBeCalled()
                    }
                }

                "top app bar shows expected chat name" {
                    runComposeUiTest {
                        setMessagesView(state = aEmptyMessagesState(name = "[I'm a chat name]"))

                        onNodeWithText("[I'm a chat name]").assertExists()
                    }
                }

                "clicking on the sender icon opens the sender sheet" {
                    runComposeUiTest {
                        setMessagesView(
                            state =
                                aMessagesStateWithMembers(
                                    name = "[I'm a chat name]",
                                    members =
                                        listOf(
                                            Member(
                                                id = MemberID("1"),
                                                name = "N",
                                                description = "",
                                                avatar = null,
                                                cover = null,
                                                preferences = "",
                                                roles = "",
                                                birth = LocalDate(2024, 1, 1),
                                                admin = false,
                                            ),
                                            Member(
                                                id = MemberID("2"),
                                                name = "N2",
                                                description = "",
                                                avatar = null,
                                                cover = null,
                                                preferences = "",
                                                roles = "",
                                                birth = LocalDate(2024, 1, 1),
                                                admin = false,
                                            ),
                                        ),
                                )
                        )

                        onNodeWithText("[I'm a chat name]").assertExists()
                        onNodeWithTag(TestTags.CHANGE_SENDER).performClick()
                        onNodeWithText("Sender").assertExists()
                        onNodeWithText("Narrator").assertExists().assertHasClickAction()
                        onNodeWithText("N").assertExists().assertHasClickAction()
                        onNodeWithText("N2").assertExists().assertHasClickAction()
                    }
                }

                "clicking on the sender in the the sender sheet invokes the change sender callback" {
                    runComposeUiTest {
                        val callback = AssertableCallbackWithParam<MessagesEvents>()
                        setMessagesView(
                            state =
                                aMessagesStateWithMembers(
                                    name = "[I'm a chat name]",
                                    sender = Sender.Member(MemberID("1")),
                                    members =
                                        listOf(
                                            Member(
                                                id = MemberID("1"),
                                                name = "N",
                                                description = "",
                                                avatar = null,
                                                cover = null,
                                                preferences = "",
                                                roles = "",
                                                birth = LocalDate(2024, 1, 1),
                                                admin = false,
                                            ),
                                            Member(
                                                id = MemberID("2"),
                                                name = "N2",
                                                description = "",
                                                avatar = null,
                                                cover = null,
                                                preferences = "",
                                                roles = "",
                                                birth = LocalDate(2024, 1, 1),
                                                admin = false,
                                            ),
                                        ),
                                    eventSink = callback,
                                )
                        )

                        onNodeWithText("[I'm a chat name]").assertExists()
                        onNodeWithTag(TestTags.CHANGE_SENDER).performClick()
                        onNodeWithText("Sender").assertExists()
                        onNodeWithText("Narrator").assertExists().assertHasClickAction().performClick()
                        onNodeWithText("N").assertExists().assertHasClickAction()
                        onNodeWithText("N2").assertExists().assertHasClickAction().performClick()

                        callback.shouldBeCalledWith(
                            listOf(
                                MessagesEvents.ChangeSender(Sender.Narrator),
                                MessagesEvents.ChangeSender(Sender.Member(MemberID("2"))),
                            )
                        )
                    }
                }

                "two succeeding messages with same sender should show only one name" {
                    runComposeUiTest {
                        setMessagesView(state = aMessagesState())

                        val nodes = onAllNodesWithText("N").fetchSemanticsNodes()
                        nodes.size shouldBe 1
                    }
                }
            }
    })

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.setMessagesView(
    state: MessagesState,
    onBack: () -> Unit = AssertNeverCalled(),
) {
    setContent {
        MessagesUI(
            state = state,
            onBack = onBack,
        )
    }
}
