package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@Composable
@ContributesComponent(SystemScope::class)
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
