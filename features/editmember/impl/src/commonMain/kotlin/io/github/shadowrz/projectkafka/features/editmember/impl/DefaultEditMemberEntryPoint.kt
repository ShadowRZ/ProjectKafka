package io.github.shadowrz.projectkafka.features.editmember.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberEntryPoint
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
class DefaultEditMemberEntryPoint : EditMemberEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        memberID: MemberID,
        callback: EditMemberEntryPoint.Callback,
    ): Component =
        parent.childComponent<EditMemberComponent>(
            context = context,
            plugins = listOf(
                EditMemberEntryPoint.Params(memberID),
                callback,
            ),
        )
}
