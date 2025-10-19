package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Poll: ImageVector
    get() {
        if (_Poll != null) {
            return _Poll!!
        }
        _Poll = ImageVector.Builder(
            name = "Poll",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(19f, 3f)
                lineTo(5f, 3f)
                curveToRelative(-1.1f, 0f, -2f, 0.9f, -2f, 2f)
                verticalLineToRelative(14f)
                curveToRelative(0f, 1.1f, 0.9f, 2f, 2f, 2f)
                horizontalLineToRelative(14f)
                curveToRelative(1.1f, 0f, 2f, -0.9f, 2f, -2f)
                lineTo(21f, 5f)
                curveToRelative(0f, -1.1f, -0.9f, -2f, -2f, -2f)
                moveToRelative(0f, 16f)
                lineTo(5f, 19f)
                lineTo(5f, 5f)
                horizontalLineToRelative(14f)
                close()
                moveTo(7f, 10f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(7f)
                lineTo(7f, 17f)
                close()
                moveTo(11f, 7f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(10f)
                horizontalLineToRelative(-2f)
                close()
                moveTo(15f, 13f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(-2f)
                close()
            }
        }.build()

        return _Poll!!
    }

@Suppress("ObjectPropertyName")
private var _Poll: ImageVector? = null
