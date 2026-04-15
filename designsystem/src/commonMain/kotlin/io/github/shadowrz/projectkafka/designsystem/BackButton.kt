package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.github.shadowrz.projectkafka.designsystem.icons.ArrowBack
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.libraries.strings.CommonStrings
import io.github.shadowrz.projectkafka.libraries.strings.common_back
import org.jetbrains.compose.resources.stringResource

@Composable
@NonRestartableComposable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageVector: ImageVector = KafkaIcons.ArrowBack,
    contentDescription: String? = stringResource(CommonStrings.common_back),
    enabled: Boolean = true,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            imageVector,
            contentDescription = contentDescription,
        )
    }
}

@Composable
@PreviewLightDark
internal fun PreviewBackButton() =
    KafkaPreview {
        Column {
            BackButton({})
            BackButton({}, enabled = false)
        }
    }
