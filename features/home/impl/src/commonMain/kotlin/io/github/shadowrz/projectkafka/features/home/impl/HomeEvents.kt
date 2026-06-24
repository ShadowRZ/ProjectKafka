package io.github.shadowrz.projectkafka.features.home.impl

import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

sealed interface HomeEvents {
    data object OpenSettings : HomeEvents

    data object OpenDataManage : HomeEvents

    data object OpenAbout : HomeEvents

    data object OpenSwitchSystem : HomeEvents

    data class CreateChat(val creatorID: MemberID) : HomeEvents

    data class SwitchShowingDialog(val showingDialog: HomeState.ShowingDialog) : HomeEvents
}
