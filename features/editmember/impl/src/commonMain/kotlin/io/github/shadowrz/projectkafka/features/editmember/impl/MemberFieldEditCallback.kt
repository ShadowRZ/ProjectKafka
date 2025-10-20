package io.github.shadowrz.projectkafka.features.editmember.impl

interface MemberFieldEditCallback {
    fun onBack()

    fun onSave(state: MemberFieldEditState.FieldState)
}
