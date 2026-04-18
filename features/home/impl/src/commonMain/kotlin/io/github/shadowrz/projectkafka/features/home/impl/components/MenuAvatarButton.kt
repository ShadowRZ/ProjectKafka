package io.github.shadowrz.projectkafka.features.home.impl.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.eygraber.uri.Uri
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.designsystem.IconButton
import io.github.shadowrz.projectkafka.designsystem.OutlinedAvatar
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.features.home.impl.SharedElements

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MenuAvatarButton(
    avatar: Uri?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SharedElementTransitionScope {
        IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            OutlinedAvatar(
                avatar = avatar,
                modifier =
                    Modifier
                        .sharedElement(
                            sharedContentState =
                                rememberSharedContentState(
                                    SharedElements.AvatarMenu,
                                ),
                            animatedVisibilityScope =
                                requireAnimatedScope(
                                    SharedElementTransitionScope
                                        .AnimatedScope
                                        .Navigation,
                                ),
                        ),
            )
        }
    }
}

@Composable
@PreviewLightDark
internal fun PreviewMenuAvatarButton() {
    KafkaPreview {
        MenuAvatarButton(
            avatar = null,
            onClick = {},
        )
    }
}
