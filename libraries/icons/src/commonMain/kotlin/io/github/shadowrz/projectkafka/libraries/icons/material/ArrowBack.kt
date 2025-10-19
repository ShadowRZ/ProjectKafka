package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.ArrowBack: ImageVector
    get() {
        if (_ArrowBack != null) {
            return _ArrowBack!!
        }
        _ArrowBack = ImageVector.Builder(
            name = "ArrowBack",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(7.825f, 13f)
                lineToRelative(5.6f, 5.6f)
                lineTo(12f, 20f)
                lineToRelative(-8f, -8f)
                lineToRelative(8f, -8f)
                lineToRelative(1.425f, 1.4f)
                lineToRelative(-5.6f, 5.6f)
                horizontalLineTo(20f)
                verticalLineToRelative(2f)
                close()
            }
        }.build()

        return _ArrowBack!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowBack: ImageVector? = null
