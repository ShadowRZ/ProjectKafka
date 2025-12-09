package io.github.shadowrz.projectkafka.features.home.impl

sealed interface HomeEvents {
    data object OpenSettings : HomeEvents

    data object OpenDataManage : HomeEvents

    data object OpenAbout : HomeEvents

    data object OpenSwitchSystem : HomeEvents

    data class SwitchShowingDialog(
        val showingDialog: HomeState.ShowingDialog,
    ) : HomeEvents
}
