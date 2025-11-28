package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import io.github.shadowrz.hanekokoro.framework.annotations.ContributesComponent
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@ContributesComponent(SystemScope::class)
internal fun EditMemberUI(
    component: EditMemberComponent,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalLifecycleOwner provides component.lifecycleOwner,
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
                supportDeleteMember = true,
                onDeleteMember = component::onDeleteMember,
            )
        }
    }
}
