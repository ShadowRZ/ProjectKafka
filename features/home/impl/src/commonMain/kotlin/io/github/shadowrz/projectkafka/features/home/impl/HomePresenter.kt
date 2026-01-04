package io.github.shadowrz.projectkafka.features.home.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.composeunstyled.rememberDialogState
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesBinding
import io.github.shadowrz.hanekokoro.framework.runtime.presenter.Presenter
import io.github.shadowrz.projectkafka.features.home.api.HomeEntryPoint
import io.github.shadowrz.projectkafka.libraries.data.api.System
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.preferences.api.AppPreferencesStore

@AssistedInject
@ContributesBinding(SystemScope::class)
class HomePresenter(
    @Assisted private val callback: HomeEntryPoint.Callback,
    private val appPreferencesStore: AppPreferencesStore,
    private val system: System,
) : Presenter<HomeState> {
    @Composable
    override fun present(): HomeState {
        var showingDialog by rememberSaveable {
            mutableStateOf(HomeState.ShowingDialog.Closed)
        }
        val dialogState = rememberDialogState()
        val allowsMultiSystem by appPreferencesStore.allowsMultiSystem().collectAsState(false)

        LaunchedEffect(showingDialog) {
            dialogState.visible = (showingDialog == HomeState.ShowingDialog.SystemMenu)
        }

        return HomeState(
            system = system,
            dialogState = dialogState,
            showingDialog = showingDialog,
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
            }
        }
    }

    @AssistedFactory
    fun interface Factory {
        fun create(callback: HomeEntryPoint.Callback): HomePresenter
    }
}
