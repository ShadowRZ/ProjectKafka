package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.icons.HourglassEmpty
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka
import org.jetbrains.compose.resources.stringResource
import projectkafka.designsystem.generated.resources.Res
import projectkafka.designsystem.generated.resources.designsystem_notready_description
import projectkafka.designsystem.generated.resources.designsystem_notready_title

@Composable
fun NotReady(
    modifier: Modifier = Modifier,
    icon: ImageVector = KafkaIcons.HourglassEmpty,
) {
    CompositionLocalProvider(LocalContentColor provides KafkaTheme.colors.onSurfaceVariant) {
        Column(
            modifier = modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(192.dp),
            )
            Text(
                stringResource(Res.string.designsystem_notready_title),
                style = KafkaTheme.typography.headlineMedium,
            )
            Text(
                stringResource(Res.string.designsystem_notready_description),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@PreviewKafka
internal fun PreviewNotReady() = KafkaPreview {
    NotReady()
}
