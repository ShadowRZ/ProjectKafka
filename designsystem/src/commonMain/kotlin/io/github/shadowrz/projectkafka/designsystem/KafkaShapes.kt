package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * A subset of Material 3 [Shapes] color tokens, along with custom tokens.
 *
 * @see androidx.compose.material3.Shapes
 */
object KafkaShapes {
    val Small: CornerBasedShape = RoundedCornerShape(size = 8.0.dp)
    val Medium: CornerBasedShape = RoundedCornerShape(size = 16.0.dp)
    val Large: CornerBasedShape = RoundedCornerShape(size = 24.0.dp)
}
