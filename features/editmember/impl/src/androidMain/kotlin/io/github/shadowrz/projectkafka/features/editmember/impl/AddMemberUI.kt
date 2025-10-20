package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@SingleIn(SystemScope::class)
@Inject
@ContributesIntoMap(
    SystemScope::class,
    binding = binding<ComponentUI<*>>(),
)
@ComponentKey(AddMemberComponent::class)
class AddMemberUI : ComponentUI<AddMemberComponent> {
    @Composable
    override fun Content(
        component: AddMemberComponent,
        modifier: Modifier,
    ) {
        val state = component.presenter.present()

        MemberFieldEditUI(
            title = stringResource(CommonStrings.common_new_member),
            state = state,
        )
    }
}
