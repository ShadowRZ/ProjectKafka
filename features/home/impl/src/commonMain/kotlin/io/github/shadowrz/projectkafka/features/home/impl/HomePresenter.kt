package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ForScope
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.libraries.data.api.ChatsStore
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.kafkastate.api.MembersPresenter
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AssistedInject
class HomePresenter(
    @Assisted private val callback: HomeCallback,
    private val appPreferencesStore: AppPreferencesStore,
    private val system: System,
    private val membersPresenter: MembersPresenter,
    private val chatsStore: ChatsStore,
    @ForScope(SystemScope::class) private val systemCoroutineScope: CoroutineScope,
) : Presenter<HomeState> {
    @Composable
    override fun present(): HomeState {
        var showingDialog by rememberSaveable {
            mutableStateOf(HomeState.ShowingDialog.Closed)
        }
        var dialogVisible by rememberSaveable {
            mutableStateOf(false)
        }
        val allowsMultiSystem by appPreferencesStore.allowsMultiSystem().collectAsState(false)
        val members = membersPresenter.present()

        LaunchedEffect(showingDialog) {
            dialogVisible = (showingDialog == HomeState.ShowingDialog.SystemMenu)
        }

        return HomeState(
            system = system,
            showingDialog = showingDialog,
            members = members.members,
            allowsMultiSystem = allowsMultiSystem,
        ) {
            when (it) {
                HomeEvents.OpenAbout -> {
                    showingDialog = HomeState.ShowingDialog.Closed
                    callback.onAbout()
                }

                HomeEvents.OpenDataManage -> {
                    showingDialog = HomeState.ShowingDialog.Closed
                    callback.onDataManage()
                }

                HomeEvents.OpenSettings -> {
                    showingDialog = HomeState.ShowingDialog.Closed
                    callback.onSettings()
                }

                HomeEvents.OpenSwitchSystem -> {
                    showingDialog = HomeState.ShowingDialog.Closed
                    callback.onSwitchSystem()
                }

                is HomeEvents.SwitchShowingDialog -> {
                    showingDialog = it.showingDialog
                }

                is HomeEvents.CreateChat -> {
                    systemCoroutineScope.launch {
                        chatsStore.addChat(
                            name = null,
                            avatar = null,
                            creatorID = it.creatorID,
                        )
                    }
                }
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(callback: HomeCallback): HomePresenter
    }
}
