package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.ImageOutline: ImageVector
    get() {
        if (_ImageOutline != null) {
            return _ImageOutline!!
        }
        _ImageOutline = ImageVector.Builder(
            name = "ImageOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(5f, 21f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(3f, 19f)
                lineTo(3f, 5f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(5f, 3f)
                horizontalLineToRelative(14f)
                quadToRelative(0.825f, 0f, 1.413f, 0.588f)
                reflectiveQuadTo(21f, 5f)
                verticalLineToRelative(14f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(19f, 21f)
                close()
                moveTo(5f, 19f)
                horizontalLineToRelative(14f)
                lineTo(19f, 5f)
                lineTo(5f, 5f)
                close()
                moveTo(6f, 17f)
                horizontalLineToRelative(12f)
                lineToRelative(-3.75f, -5f)
                lineToRelative(-3f, 4f)
                lineTo(9f, 13f)
                close()
                moveTo(5f, 19f)
                lineTo(5f, 5f)
                close()
            }
        }.build()

        return _ImageOutline!!
    }

@Suppress("ObjectPropertyName")
private var _ImageOutline: ImageVector? = null
