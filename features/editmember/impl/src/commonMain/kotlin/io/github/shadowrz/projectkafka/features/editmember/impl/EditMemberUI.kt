package io.github.shadowrz.projectkafka.features.editmember.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import io.github.shadowrz.hanekokoro.framework.annotations.HanekokoroInject
import io.github.shadowrz.projectkafka.libraries.components.BackHandler
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.di.SystemScope
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_edit_member
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
@HanekokoroInject.ContributesRenderer(SystemScope::class)
internal fun EditMemberUI(
    component: EditMemberComponent,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalLifecycleOwner provides component.lifecycleOwner,
    ) {
        when (val state = component.presenter.present()) {
            Result.Loading -> {
                LoadingIndicator(
                    modifier = modifier.fillMaxSize().wrapContentSize(),
                )
            }

            is Result.Success<MemberFieldEditState> -> {
                BackHandler(
                    backHandler = component.backHandler,
                    isEnabled = state.value.dirty,
                    onBack = { state.value.eventSink(MemberFieldEditEvents.Back) },
                )

                MemberFieldEditUI(
                    modifier = modifier,
                    title = stringResource(CommonStrings.common_edit_member),
                    state = state.value,
                    supportDeleteMember = true,
                    onDeleteMember = component::onDeleteMember,
                )
            }
        }
    }
}
