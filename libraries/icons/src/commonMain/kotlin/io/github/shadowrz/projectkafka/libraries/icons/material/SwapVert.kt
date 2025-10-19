package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.SwapVert: ImageVector
    get() {
        if (_SwapVert != null) {
            return _SwapVert!!
        }
        _SwapVert = ImageVector.Builder(
            name = "SwapVert",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(8f, 13f)
                lineTo(8f, 5.825f)
                lineTo(5.425f, 8.4f)
                lineTo(4f, 7f)
                lineToRelative(5f, -5f)
                lineToRelative(5f, 5f)
                lineToRelative(-1.425f, 1.4f)
                lineTo(10f, 5.825f)
                lineTo(10f, 13f)
                close()
                moveTo(15f, 22f)
                lineToRelative(-5f, -5f)
                lineToRelative(1.425f, -1.4f)
                lineTo(14f, 18.175f)
                lineTo(14f, 11f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(7.175f)
                lineToRelative(2.575f, -2.575f)
                lineTo(20f, 17f)
                close()
            }
        }.build()

        return _SwapVert!!
    }

@Suppress("ObjectPropertyName")
private var _SwapVert: ImageVector? = null
