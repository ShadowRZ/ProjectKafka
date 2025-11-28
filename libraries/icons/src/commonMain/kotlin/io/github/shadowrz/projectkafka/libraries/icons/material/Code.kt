package io.github.shadowrz.projectkafka.libraries.icons.material

import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaterialIcons.Code: ImageVector
    get() {
        if (_Code != null) {
            return _Code!!
        }
        _Code = ImageVector.Builder(
            name = "Code",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(8f, 18f)
                lineToRelative(-6f, -6f)
                lineToRelative(6f, -6f)
                lineToRelative(1.425f, 1.425f)
                lineToRelative(-4.6f, 4.6f)
                lineTo(9.4f, 16.6f)
                close()
                moveTo(16f, 18f)
                lineToRelative(-1.425f, -1.425f)
                lineToRelative(4.6f, -4.6f)
                lineTo(14.6f, 7.4f)
                lineTo(16f, 6f)
                lineToRelative(6f, 6f)
                close()
            }
        }.build()

        return _Code!!
    }

@Suppress("ObjectPropertyName")
private var _Code: ImageVector? = null
