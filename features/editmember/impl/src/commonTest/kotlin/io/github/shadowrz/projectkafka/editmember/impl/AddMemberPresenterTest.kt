package io.github.shadowrz.projectkafka.editmember.impl

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import app.cash.turbine.test
import com.attafitamim.krop.core.crop.imageCropper
import com.eygraber.uri.toKmpUri
import io.github.shadowrz.projectkafka.features.editmember.impl.AddMemberComponent
import io.github.shadowrz.projectkafka.features.editmember.impl.AddMemberPresenter
import io.github.shadowrz.projectkafka.features.editmember.impl.MemberFieldEditEvents
import io.github.shadowrz.projectkafka.features.editmember.impl.MemberFieldEditPresenter
import io.github.shadowrz.projectkafka.libraries.core.extensions.isNullOrEmpty
import io.github.shadowrz.projectkafka.libraries.data.test.InMemoryMembersStore
import io.github.shadowrz.projectkafka.libraries.profile.test.FakeCropperProvider
import io.github.shadowrz.projectkafka.tests.utils.test
import io.github.shadowrz.projectkafka.tests.utils.warmUpMolecule
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.StringSpec
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

class AddMemberPresenterTest :
    StringSpec({
        warmUpMolecule()

        "presenter - initial state" {
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
        }

        "presenter - when name is non empty, valid should be true" {
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
        }

        "presenter - when name has changed, dirty should be true" {
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
        }

        "presenter - when description has changed, dirty should be true" {
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
        }

        "presenter - when avatar has changed, dirty should be true" {
            runTest {
                val selectProfileProvider = FakeCropperProvider()
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
        }

        "presenter - when preferences has changed, dirty should be true" {
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
        }

        "presenter - when roles has changed, dirty should be true" {
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
        }

        "presenter - when birth has changed, dirty should be true" {
            runTest {
                presenter().test {
                    var state = awaitItem()
                    assertSoftly(state) {
                        birth.shouldBeNull()
                        dirty.shouldBeFalse()
                    }
                    state.eventSink(MemberFieldEditEvents.ChangeBirth(LocalDate(2024, 1, 1)))
                    state = awaitItem()
                    assertSoftly(state) {
                        birth shouldBe LocalDate(2024, 1, 1)
                        dirty.shouldBeTrue()
                    }
                }
            }
        }

        "presenter - when admin has changed, dirty should be true" {
            runTest {
                presenter().test {
                    var state = awaitItem()
                    assertSoftly(state) {
                        admin.shouldBeFalse()
                        dirty.shouldBeFalse()
                    }
                    state.eventSink(MemberFieldEditEvents.ChangeAdmin(true))
                    state = awaitItem()
                    assertSoftly(state) {
                        admin.shouldBeTrue()
                        dirty.shouldBeTrue()
                    }
                }
            }
        }

        "presenter - when dirty is false and back event was dispatched, onFinish callback should be called" {
            runTest {
                var callbackCalled = false
                presenter(onFinish = { callbackCalled = true }).test {
                    val state = awaitItem()
                    state.dirty.shouldBeFalse()
                    state.eventSink(MemberFieldEditEvents.Back)
                    callbackCalled.shouldBeTrue()
                }
            }
        }

        "presenter - when dirty is true and back event was dispatched, showDirtyDialog should be true" {
            runTest {
                presenter().test {
                    var state = awaitItem()
                    state.name.setTextAndPlaceCursorAtEnd("N")
                    state = awaitItem()
                    assertSoftly(state) {
                        name.text.toString() shouldBe "N"
                        dirty.shouldBeTrue()
                    }
                    state.eventSink(MemberFieldEditEvents.Back)
                    state = awaitItem()
                    state.showDirtyDialog.shouldBeTrue()
                }
            }
        }

        "presenter - when showDirtyDialog is true and discard changes event was dispatched, close dialog and call callback" {
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
                    state.eventSink(MemberFieldEditEvents.Back)
                    state = awaitItem()
                    state.showDirtyDialog.shouldBeTrue()
                    state.eventSink(MemberFieldEditEvents.DiscardChanges)
                    state = awaitItem()
                    state.showDirtyDialog.shouldBeFalse()
                    callbackCalled.shouldBeTrue()
                }
            }
        }

        "presenter - when showDirtyDialog is true and cancel event was dispatched, close dialog only" {
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
                    state.eventSink(MemberFieldEditEvents.Back)
                    state = awaitItem()
                    state.showDirtyDialog.shouldBeTrue()
                    state.eventSink(MemberFieldEditEvents.CloseDirtyDialog)
                    state = awaitItem()
                    state.showDirtyDialog.shouldBeFalse()
                    callbackCalled.shouldBeFalse()
                }
            }
        }

        "presenter - when valid is false, saving should not change state" {
            runTest {
                presenter().test {
                    val state = awaitItem()
                    assertSoftly(state) {
                        name.text.toString().shouldBeEmpty()
                        valid.shouldBeFalse()
                    }
                    state.eventSink(MemberFieldEditEvents.Save)
                }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        "presenter - when valid is true, saving should cause member detail to be saved" {
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
                    state.eventSink(MemberFieldEditEvents.Save)
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
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        "presenter - when valid is true, saving a member with birth" {
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
                    state.eventSink(MemberFieldEditEvents.ChangeBirth(LocalDate(2024, 1, 1)))
                    state = awaitItem()
                    state.birth shouldBe LocalDate(2024, 1, 1)
                    state.eventSink(MemberFieldEditEvents.Save)
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
    })

private fun TestScope.presenter(
    onFinish: () -> Unit = {},
    membersStore: InMemoryMembersStore = InMemoryMembersStore(),
    selectProfileProvider: FakeCropperProvider = FakeCropperProvider(),
): AddMemberPresenter {
    val callback =
        object : AddMemberComponent.Callback {
            override fun onFinish() {
                onFinish()
            }
        }
    val presenterFactory = MemberFieldEditPresenter.Factory { initialState, imageCropper, callback ->
        MemberFieldEditPresenter(
            imageCropper = imageCropper,
            initialState = initialState,
            callback = callback,
            cropperProvider = selectProfileProvider,
        )
    }
    val presenter =
        AddMemberPresenter(
            callback = callback,
            imageCropper = imageCropper(),
            systemCoroutineScope = this,
            presenterFactory = presenterFactory,
            membersStore = membersStore,
        )

    return presenter
}
