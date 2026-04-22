package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ListIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    color: Color = KafkaTheme.materialColors.surfaceVariant,
) {
    Box(
        modifier = modifier.size(40.dp).clip(CircleShape).background(color),
    ) {
        Icon(
            imageVector,
            modifier = Modifier.align(Alignment.Center),
            contentDescription = contentDescription,
            tint = KafkaTheme.materialColors.onSurfaceVariant,
        )
    }
}
