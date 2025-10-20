package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentKey
import io.github.shadowrz.projectkafka.libraries.architecture.ComponentUI
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@SingleIn(SystemScope::class)
@Inject
@ContributesIntoMap(
    SystemScope::class,
    binding = binding<ComponentUI<*>>(),
)
@ComponentKey(EditMemberComponent::class)
class EditMemberUI : ComponentUI<EditMemberComponent> {
    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    override fun Content(
        component: EditMemberComponent,
        modifier: Modifier,
    ) {
        val state = component.presenter.present()

        when (state) {
            Result.Loading -> LoadingIndicator(
                modifier = modifier.fillMaxSize().wrapContentSize(),
            )
            is Result.Success<MemberFieldEditState> -> MemberFieldEditUI(
                modifier = modifier,
                title = stringResource(CommonStrings.common_edit_member),
                state = state.value,
            )
        }
    }
}
