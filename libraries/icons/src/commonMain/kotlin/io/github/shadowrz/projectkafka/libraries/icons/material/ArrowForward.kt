package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.ArrowForward: ImageVector
    get() {
        if (_ArrowForward != null) {
            return _ArrowForward!!
        }
        _ArrowForward = ImageVector.Builder(
            name = "ArrowForward",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(16.175f, 13f)
                horizontalLineTo(4f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(12.175f)
                lineToRelative(-5.6f, -5.6f)
                lineTo(12f, 4f)
                lineToRelative(8f, 8f)
                lineToRelative(-8f, 8f)
                lineToRelative(-1.425f, -1.4f)
                close()
            }
        }.build()

        return _ArrowForward!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowForward: ImageVector? = null
