package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import app.cash.turbine.test
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.toKmpUri
import io.github.shadowrz.projectkafka.libraries.core.extensions.isNullOrEmpty
import io.github.shadowrz.projectkafka.libraries.data.test.InMemoryMembersStore
import io.github.shadowrz.projectkafka.libraries.mediapickers.test.FakePickerProvider
import io.github.shadowrz.projectkafka.libraries.profile.test.FakeSelectProfileProvider
import io.github.shadowrz.projectkafka.tests.utils.WarmUpRule
import io.github.shadowrz.projectkafka.tests.utils.test
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Rule
import kotlin.test.Test

class AddMemberPresenterTest {
    @get:Rule
    val warmUp = WarmUpRule()

    @Test
    fun `presenter - initial state`() =
        runTest {
            presenter().test {
                val state = awaitItem()
                assertSoftly(state) {
                    name.text.toString().shouldBeEmpty()
                    description.text.toString().shouldBeEmpty()
                    avatar.value.isNullOrEmpty().shouldBeTrue()
                    preferences.text.toString().shouldBeEmpty()
                    roles.text.toString().shouldBeEmpty()
                    birth.shouldBeNull()
                    admin.shouldBeFalse()
                    valid.shouldBeFalse()
                    dirty.shouldBeFalse()
                    showDirtyDialog.shouldBeFalse()
                    saving.shouldBeFalse()
                }
            }
        }

    @Test
    fun `presenter - when name is non empty, valid should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    name.text.toString().shouldBeEmpty()
                    valid.shouldBeFalse()
                }
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    valid.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when name has changed, dirty should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    name.text.toString().shouldBeEmpty()
                    dirty.shouldBeFalse()
                }
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when description has changed, dirty should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    description.text.toString().shouldBeEmpty()
                    dirty.shouldBeFalse()
                }
                state.description.setTextAndPlaceCursorAtEnd("Hello world")
                state = awaitItem()
                assertSoftly(state) {
                    description.text.toString() shouldBe "Hello world"
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when avatar has changed, dirty should be true`() =
        runTest {
            val selectProfileProvider = FakeSelectProfileProvider()
            presenter(selectProfileProvider = selectProfileProvider).test {
                var state = awaitItem()
                assertSoftly(state) {
                    avatar.value.isNullOrEmpty().shouldBeTrue()
                    dirty.shouldBeFalse()
                }
                selectProfileProvider.avatar.emit("https://example.com/avatar.png".toKmpUri())
                state = awaitItem()
                assertSoftly(state) {
                    avatar.value.toString() shouldBe "https://example.com/avatar.png"
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when preferences has changed, dirty should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    preferences.text.toString().shouldBeEmpty()
                    dirty.shouldBeFalse()
                }
                state.preferences.setTextAndPlaceCursorAtEnd("Hello world")
                state = awaitItem()
                assertSoftly(state) {
                    preferences.text.toString() shouldBe "Hello world"
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when roles has changed, dirty should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    roles.text.toString().shouldBeEmpty()
                    dirty.shouldBeFalse()
                }
                state.roles.setTextAndPlaceCursorAtEnd("Hello world")
                state = awaitItem()
                assertSoftly(state) {
                    roles.text.toString() shouldBe "Hello world"
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when birth has changed, dirty should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    birth.shouldBeNull()
                    dirty.shouldBeFalse()
                }
                state.eventSink(AddMemberEvents.ChangeBirth(LocalDate(2024, 1, 1)))
                state = awaitItem()
                assertSoftly(state) {
                    birth shouldBe LocalDate(2024, 1, 1)
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when admin has changed, dirty should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                assertSoftly(state) {
                    admin.shouldBeFalse()
                    dirty.shouldBeFalse()
                }
                state.eventSink(AddMemberEvents.ChangeAdmin(true))
                state = awaitItem()
                assertSoftly(state) {
                    admin.shouldBeTrue()
                    dirty.shouldBeTrue()
                }
            }
        }

    @Test
    fun `presenter - when dirty is false and back event was dispatched, onFinish callback should be called`() =
        runTest {
            var callbackCalled = false
            presenter(onFinish = { callbackCalled = true }).test {
                val state = awaitItem()
                state.dirty.shouldBeFalse()
                state.eventSink(AddMemberEvents.Back)
                callbackCalled.shouldBeTrue()
            }
        }

    @Test
    fun `presenter - when dirty is true and back event was dispatched, showDirtyDialog should be true`() =
        runTest {
            presenter().test {
                var state = awaitItem()
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    dirty.shouldBeTrue()
                }
                state.eventSink(AddMemberEvents.Back)
                state = awaitItem()
                state.showDirtyDialog.shouldBeTrue()
            }
        }

    @Test
    fun `presenter - when showDirtyDialog is true and discard changes event was dispatched, close dialog and call callback`() =
        runTest {
            var callbackCalled = false
            presenter(onFinish = { callbackCalled = true }).test {
                var state = awaitItem()
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    dirty.shouldBeTrue()
                }
                state.eventSink(AddMemberEvents.Back)
                state = awaitItem()
                state.showDirtyDialog.shouldBeTrue()
                state.eventSink(AddMemberEvents.DiscardChanges)
                state = awaitItem()
                state.showDirtyDialog.shouldBeFalse()
                callbackCalled.shouldBeTrue()
            }
        }

    @Test
    fun `presenter - when showDirtyDialog is true and cancel event was dispatched, close dialog only`() =
        runTest {
            var callbackCalled = false
            presenter(onFinish = { callbackCalled = true }).test {
                var state = awaitItem()
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    dirty.shouldBeTrue()
                }
                state.eventSink(AddMemberEvents.Back)
                state = awaitItem()
                state.showDirtyDialog.shouldBeTrue()
                state.eventSink(AddMemberEvents.CloseDirtyDialog)
                state = awaitItem()
                state.showDirtyDialog.shouldBeFalse()
                callbackCalled.shouldBeFalse()
            }
        }

    @Test
    fun `presenter - when valid is false, saving should not change state`() =
        runTest {
            presenter().test {
                val state = awaitItem()
                assertSoftly(state) {
                    name.text.toString().shouldBeEmpty()
                    valid.shouldBeFalse()
                }
                state.eventSink(AddMemberEvents.Save)
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `presenter - when valid is true, saving should cause member detail to be saved`() =
        runTest {
            val membersStore = InMemoryMembersStore()
            var callbackCalled = false
            presenter(
                onFinish = { callbackCalled = true },
                membersStore = membersStore,
            ).test {
                var state = awaitItem()
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    dirty.shouldBeTrue()
                }
                state.eventSink(AddMemberEvents.Save)
                state = awaitItem()
                state.saving.shouldBeTrue()
                advanceUntilIdle()
                state = awaitItem()
                state.saving.shouldBeFalse()
                callbackCalled.shouldBeTrue()
                membersStore.membersCount().test {
                    awaitItem() shouldBe 1
                }
                membersStore.getMembers().test {
                    val members = awaitItem()
                    members.shouldHaveSize(1)
                    members[0].name shouldBe "N"
                }
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `presenter - when valid is true, saving a member with birth`() =
        runTest {
            val membersStore = InMemoryMembersStore()
            var callbackCalled = false
            presenter(
                onFinish = { callbackCalled = true },
                membersStore = membersStore,
            ).test {
                var state = awaitItem()
                state.name.setTextAndPlaceCursorAtEnd("N")
                state = awaitItem()
                assertSoftly(state) {
                    name.text.toString() shouldBe "N"
                    dirty.shouldBeTrue()
                }
                state.eventSink(AddMemberEvents.ChangeBirth(LocalDate(2024, 1, 1)))
                state = awaitItem()
                state.birth shouldBe LocalDate(2024, 1, 1)
                state.eventSink(AddMemberEvents.Save)
                state = awaitItem()
                state.saving.shouldBeTrue()
                advanceUntilIdle()
                state = awaitItem()
                state.saving.shouldBeFalse()
                callbackCalled.shouldBeTrue()
                membersStore.membersCount().test {
                    awaitItem() shouldBe 1
                }
                membersStore.getMembers().test {
                    val members = awaitItem()
                    members.shouldHaveSize(1)
                    assertSoftly(members[0]) {
                        name shouldBe "N"
                        birth shouldBe LocalDate(2024, 1, 1)
                    }
                }
            }
        }
}

private fun TestScope.presenter(
    onFinish: () -> Unit = {},
    membersStore: InMemoryMembersStore = InMemoryMembersStore(),
    selectProfileProvider: FakeSelectProfileProvider = FakeSelectProfileProvider(),
): AddMemberPresenter {
    val callback =
        object : AddMemberComponent.Callback {
            override fun onFinish() {
                onFinish()
            }
        }
    val pickerProvider = FakePickerProvider()
    val presenter =
        AddMemberPresenter(
            callback = callback,
            imageCropper = imageCropper(),
            pickerProvider = pickerProvider,
            selectProfileProvider = selectProfileProvider,
            systemCoroutineScope = this,
            membersStore = membersStore,
        )

    return presenter
}
