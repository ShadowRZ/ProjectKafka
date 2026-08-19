package io.github.shadowrz.projectkafka.features.home.impl.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.OutlinedAvatar
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka
import io.github.shadowrz.projectkafka.features.home.impl.SharedElements

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MenuAvatarButton(
    avatar: String?,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            OutlinedAvatar(
                avatar = avatar,
                modifier =
                    Modifier.sharedElement(
                        sharedContentState = rememberSharedContentState(SharedElements.AvatarMenu),
                        animatedVisibilityScope = animatedVisibilityScope,
                    ),
            )
        }
    }
}

@Composable
@PreviewKafka
internal fun PreviewMenuAvatarButton() {
    KafkaPreview {
        SharedTransitionScope {
            AnimatedVisibility(visible = true, modifier = it) {
                MenuAvatarButton(
                    modifier = it,
                    avatar = null,
                    sharedTransitionScope = this@SharedTransitionScope,
                    animatedVisibilityScope = this,
                    onClick = {},
                )
            }
        }
    }
}
