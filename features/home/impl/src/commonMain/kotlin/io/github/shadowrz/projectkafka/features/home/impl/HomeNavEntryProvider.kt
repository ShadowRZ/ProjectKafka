package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import io.github.shadowrz.projectkafka.designsystem.Text
import io.github.shadowrz.projectkafka.designsystem.navigation3.ListDetailSceneStrategy
import io.github.shadowrz.projectkafka.features.about.api.AboutScreen
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageScreen
import io.github.shadowrz.projectkafka.features.editmember.api.AddMemberScreen
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberScreen
import io.github.shadowrz.projectkafka.features.home.api.HomeScreen
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsContent
import io.github.shadowrz.projectkafka.features.home.impl.chats.ChatsPresenter
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewCallback
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewContent
import io.github.shadowrz.projectkafka.features.home.impl.overview.OverviewPresenter
import io.github.shadowrz.projectkafka.features.home.impl.polls.PollsContent
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelineContent
import io.github.shadowrz.projectkafka.features.home.impl.timeline.TimelinePresenter
import io.github.shadowrz.projectkafka.features.messages.api.MessagesScreen
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesScreen
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileScreen
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemScreen
import io.github.shadowrz.projectkafka.libraries.architecture.NavEntryProvider
import io.github.shadowrz.projectkafka.libraries.architecture.Navigator
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesIntoSet(SystemScope::class)
class HomeNavEntryProvider(
    private val presenterFactory: HomePresenter.Factory,
    private val overviewPresenterFactory: OverviewPresenter.Factory,
    private val timelinePresenter: TimelinePresenter,
    private val chatsPresenter: ChatsPresenter,
) : NavEntryProvider {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun EntryProviderScope<NavKey>.provideEntry(navigator: Navigator, sharedTransitionScope: SharedTransitionScope) {
        entry<HomeScreen>(
            metadata =
                ListDetailSceneStrategy.listPane {
                    Text(
                        "Coming soon!",
                        modifier = Modifier.fillMaxSize().wrapContentSize(),
                    )
                }
        ) {
            val presenter = remember {
                presenterFactory.create(
                    object : HomeCallback {
                        override fun onAbout() {
                            navigator.navigateTo(AboutScreen)
                        }

                        override fun onAddMember() {
                            navigator.navigateTo(AddMemberScreen)
                        }

                        override fun onEditMember(memberID: MemberID) {
                            navigator.navigateTo(EditMemberScreen(memberID))
                        }

                        override fun onDataManage() {
                            navigator.navigateTo(DataManageScreen)
                        }

                        override fun onSwitchSystem() {
                            navigator.navigateTo(SwitchSystemScreen)
                        }

                        override fun onSettings() {
                            navigator.navigateTo(PreferencesScreen)
                        }
                    }
                )
            }
            val state = presenter.present()

            var navTarget by
                rememberSerializable(configuration = HomeNavTarget.CONFIG) {
                    mutableStateOf<HomeNavTarget>(HomeNavTarget.Overview)
                }

            LookaheadScope {
                HomeUI(
                    state = state,
                    navTarget = navTarget,
                    onNewNavTarget = { navTarget = it },
                    floatingActionButton = {
                        FloatingActionButton(
                            lookaheadScope = this@LookaheadScope,
                            onAddMember = { navigator.navigateTo(AddMemberScreen) },
                            onAddChat = {
                                state.eventSink(HomeEvents.SwitchShowingDialog(HomeState.ShowingDialog.NewChatCreator))
                            },
                        )
                    },
                    lookaheadScope = this@LookaheadScope,
                ) { innerPadding ->
                    AnimatedContent(
                        navTarget,
                        modifier = Modifier.fillMaxSize().padding(innerPadding).consumeWindowInsets(innerPadding),
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                    ) { state ->
                        when (state) {
                            HomeNavTarget.Overview -> {
                                val presenter = remember {
                                    overviewPresenterFactory.create(
                                        object : OverviewCallback {
                                            override fun onAddMember() {
                                                navigator.navigateTo(AddMemberScreen)
                                            }
                                        }
                                    )
                                }
                                val state = presenter.present()
                                OverviewContent(
                                    state = state,
                                    onMemberClick = { navigator.navigateTo(MemberProfileScreen(it)) },
                                )
                            }
                            HomeNavTarget.Timeline -> {
                                val state = timelinePresenter.present()
                                TimelineContent(state = state)
                            }
                            HomeNavTarget.Chats -> {
                                val state = chatsPresenter.present()
                                ChatsContent(
                                    state = state,
                                    onOpenChat = { navigator.navigateTo(MessagesScreen(it)) },
                                )
                            }
                            HomeNavTarget.Polls -> {
                                PollsContent()
                            }
                        }
                    }
                }
            }
        }
    }
}
