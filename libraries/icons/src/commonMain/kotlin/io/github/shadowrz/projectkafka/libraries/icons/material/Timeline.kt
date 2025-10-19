package io.github.shadowrz.projectkafka.libraries.icons.material

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import io.github.shadowrz.projectkafka.libraries.icons.MaterialIcons

val MaterialIcons.Timeline: ImageVector
    get() {
        if (_Timeline != null) {
            return _Timeline!!
        }
        _Timeline = ImageVector.Builder(
            name = "Timeline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3f, 18f)
                quadToRelative(-0.825f, 0f, -1.412f, -0.587f)
                reflectiveQuadTo(1f, 16f)
                reflectiveQuadToRelative(0.588f, -1.412f)
                reflectiveQuadTo(3f, 14f)
                horizontalLineToRelative(0.263f)
                quadToRelative(0.112f, 0f, 0.237f, 0.05f)
                lineTo(8.05f, 9.5f)
                quadTo(8f, 9.375f, 8f, 9.262f)
                verticalLineTo(9f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(10f, 7f)
                reflectiveQuadToRelative(1.413f, 0.588f)
                reflectiveQuadTo(12f, 9f)
                quadToRelative(0f, 0.05f, -0.05f, 0.5f)
                lineToRelative(2.55f, 2.55f)
                quadToRelative(0.125f, -0.05f, 0.238f, -0.05f)
                horizontalLineToRelative(0.525f)
                quadToRelative(0.112f, 0f, 0.237f, 0.05f)
                lineToRelative(3.55f, -3.55f)
                quadTo(19f, 8.375f, 19f, 8.262f)
                verticalLineTo(8f)
                quadToRelative(0f, -0.825f, 0.588f, -1.412f)
                reflectiveQuadTo(21f, 6f)
                reflectiveQuadToRelative(1.413f, 0.588f)
                reflectiveQuadTo(23f, 8f)
                reflectiveQuadToRelative(-0.587f, 1.413f)
                reflectiveQuadTo(21f, 10f)
                horizontalLineToRelative(-0.262f)
                quadToRelative(-0.113f, 0f, -0.238f, -0.05f)
                lineToRelative(-3.55f, 3.55f)
                quadToRelative(0.05f, 0.125f, 0.05f, 0.238f)
                verticalLineTo(14f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(15f, 16f)
                reflectiveQuadToRelative(-1.412f, -0.587f)
                reflectiveQuadTo(13f, 14f)
                verticalLineToRelative(-0.262f)
                quadToRelative(0f, -0.113f, 0.05f, -0.238f)
                lineToRelative(-2.55f, -2.55f)
                quadToRelative(-0.125f, 0.05f, -0.238f, 0.05f)
                horizontalLineTo(10f)
                quadToRelative(-0.05f, 0f, -0.5f, -0.05f)
                lineTo(4.95f, 15.5f)
                quadToRelative(0.05f, 0.125f, 0.05f, 0.238f)
                verticalLineTo(16f)
                quadToRelative(0f, 0.825f, -0.587f, 1.413f)
                reflectiveQuadTo(3f, 18f)
            }
        }.build()

        return _Timeline!!
    }

@Suppress("ObjectPropertyName")
private var _Timeline: ImageVector? = null
