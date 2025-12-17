package io.github.shadowrz.projectkafka.features.profile.impl

import com.arkivanov.decompose.ComponentContext
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.integration.childComponent
import io.github.shadowrz.hanekokoro.framework.runtime.component.Component
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileEntryPoint
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import io.github.shadowrz.projectkafka.libraries.di.SystemScope

@Inject
@ContributesBinding(SystemScope::class)
class DefaultMemberProfileEntryPoint : MemberProfileEntryPoint {
    override fun build(
        parent: Component,
        context: ComponentContext,
        memberID: MemberID,
        callback: MemberProfileEntryPoint.Callback,
    ): Component =
        parent.childComponent<MemberProfileComponent>(
            context = context,
            plugins = listOf(
                MemberProfileEntryPoint.Params(memberID),
                callback,
            ),
        )
}
