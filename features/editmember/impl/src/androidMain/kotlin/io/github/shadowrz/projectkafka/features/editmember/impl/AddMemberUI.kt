package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_new_member
import org.jetbrains.compose.resources.stringResource

@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun AddMemberUI(
    component: AddMemberComponent,
    modifier: Modifier = Modifier,
) {
    val state = component.presenter.present()

    MemberFieldEditUI(
        title = stringResource(CommonStrings.common_new_member),
        state = state,
        modifier = modifier,
    )
}
