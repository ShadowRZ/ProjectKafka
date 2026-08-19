package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.designsystem.icons.HourglassEmpty
import io.github.shadowrz.projectkafka.designsystem.preview.KafkaPreview
import io.github.shadowrz.projectkafka.designsystem.preview.PreviewKafka

@Composable
fun NotReady(
    modifier: Modifier = Modifier,
    icon: ImageVector = KafkaIcons.HourglassEmpty,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(192.dp),
        )
        Text(
            "Coming soon!",
            style = KafkaTheme.typography.headlineMedium,
        )
        Text("The features are not implemented currently.")
    }
}

@Composable
@PreviewKafka
internal fun PreviewNotReady() = KafkaPreview {
    NotReady()
}
