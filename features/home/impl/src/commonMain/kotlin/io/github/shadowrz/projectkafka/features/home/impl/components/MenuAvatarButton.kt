package io.github.shadowrz.projectkafka.features.home.impl.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eygraber.uri.Uri
import com.slack.circuit.sharedelements.SharedElementTransitionScope
import io.github.shadowrz.projectkafka.libraries.components.OutlinedAvatar
import io.github.shadowrz.projectkafka.libraries.components.SharedElements
import io.github.shadowrz.projectkafka.libraries.components.preview.ProjectKafkaPreview

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

@Preview(name = "MenuAvatarButton")
@Composable
private fun PreviewMenuAvatarButton() {
    ProjectKafkaPreview {
        MenuAvatarButton(
            avatar = null,
            onClick = {},
        )
    }
}
