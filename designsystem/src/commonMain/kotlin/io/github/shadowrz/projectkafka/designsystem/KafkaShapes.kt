package io.github.shadowrz.projectkafka.designsystem

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.RectangleShape

/**
 * A subset of Material 3 [Shapes] color tokens, along with custom tokens.
 *
 * @see androidx.compose.material3.Shapes
 */
@Immutable
data class KafkaShapes(
    val extraSmall: CornerBasedShape = ShapeDefaults.ExtraSmall,
    val small: CornerBasedShape = ShapeDefaults.Small,
    val medium: CornerBasedShape = ShapeDefaults.Medium,
    val large: CornerBasedShape = ShapeDefaults.Large,
    val extraLarge: CornerBasedShape = ShapeDefaults.ExtraLarge,
) {
    internal constructor(shapes: Shapes) : this(
        extraSmall = shapes.extraSmall,
        small = shapes.small,
        medium = shapes.medium,
        large = shapes.large,
        extraLarge = shapes.extraLarge,
    )

    val full = CircleShape
    val none = RectangleShape
}
