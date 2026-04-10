package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BigIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(56.dp).clip(RoundedCornerShape(16.dp)).background(KafkaTheme.materialColors.surfaceVariant),
    ) {
        Icon(
            imageVector,
            modifier = Modifier.align(Alignment.Center),
            contentDescription = contentDescription,
            tint = KafkaTheme.materialColors.onSurfaceVariant,
        )
    }
}
