package io.github.shadowrz.projectkafka.features.home.impl

import io.github.shadowrz.projectkafka.libraries.data.api.MemberID

interface HomeCallback {
    fun onAbout()

    fun onAddMember()

    fun onEditMember(memberID: MemberID)

    fun onDataManage()

    fun onSwitchSystem()

    fun onSettings()
}
