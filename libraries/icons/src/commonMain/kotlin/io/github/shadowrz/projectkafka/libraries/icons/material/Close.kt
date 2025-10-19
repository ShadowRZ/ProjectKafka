package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Close: ImageVector
    get() {
        if (_Close != null) {
            return _Close!!
        }
        _Close = ImageVector.Builder(
            name = "Close",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(6.4f, 19f)
                lineTo(5f, 17.6f)
                lineToRelative(5.6f, -5.6f)
                lineTo(5f, 6.4f)
                lineTo(6.4f, 5f)
                lineToRelative(5.6f, 5.6f)
                lineTo(17.6f, 5f)
                lineTo(19f, 6.4f)
                lineTo(13.4f, 12f)
                lineToRelative(5.6f, 5.6f)
                lineToRelative(-1.4f, 1.4f)
                lineToRelative(-5.6f, -5.6f)
                close()
            }
        }.build()

        return _Close!!
    }

@Suppress("ObjectPropertyName")
private var _Close: ImageVector? = null
